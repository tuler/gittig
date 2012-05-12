package gittig

import org.codehaus.groovy.grails.support.PersistenceContextInterceptor

class QueueService {

	static transactional = false
	
	def gitService
	
	def grailsApplication
	
	PersistenceContextInterceptor persistenceInterceptor

	def taskExecutor
	
	def list(params) {
		// list completed/cancelled/discarded/error jobs from this date on
		def queueTimeout = grailsApplication.config.app.queueTimeout
		def date = new Date(System.currentTimeMillis() - (1000 * queueTimeout))

		HookJob.withCriteria {
			or {
				eq('status', HookJob.HookJobStatus.QUEUED)
				eq('status', HookJob.HookJobStatus.WAITING)
				eq('status', HookJob.HookJobStatus.RUNNING)
				ge('dateCreated', date)
			}
		}
	}
	
	def enqueue(url) {
		new HookJob(url: url, status: HookJob.HookJobStatus.QUEUED).save(failOnError: true, flush: true)
	}
	
	def dequeue() {
		HookJob.withNewSession {
			def queued = HookJob.executeQuery "from HookJob j where j.status = ? and j.url not in (select jr.url from HookJob jr where jr.status = ? or jr.status = ?)", 
				[HookJob.HookJobStatus.QUEUED, HookJob.HookJobStatus.WAITING, HookJob.HookJobStatus.RUNNING]
			if (queued) {
				def job = queued.first()
				log.info "Dequeuing job for ${job.url}"

				// initialize the progress and set to WAITING status
				job.progress = new HookJobProgress(job: job)
				job.status = HookJob.HookJobStatus.WAITING
				job.save(failOnError: true, flush: true)
				
				// discard other queue jobs of the same url
				def discard = HookJob.withCriteria {
					eq('status', HookJob.HookJobStatus.QUEUED)
					eq('url', job.url)
				}.each {
					it.status = HookJob.HookJobStatus.DISCARDED
					it.result = "${job.id}"
					it.save(failOnError: true, flush: true)
				}
				return job
			}
		}
	}
	
	def dequeueAndRun() {
		def job = dequeue()
		if (job) {
			// submit the job for background execution
			def jobId = job.id
			def url = job.url
			taskExecutor.submit {
				persistenceInterceptor.init()
				try {
					// set job to RUNNING state
					HookJob.withNewSession {
						def j = HookJob.get(jobId)
						j.status = HookJob.HookJobStatus.RUNNING
						j.save(failOnError: true, flush: true)
						log.info "Running job for ${j.url}"
					}
					
					def status
					def error
					def result
					try {
						def hookJobProgressMonitor = new HookJobProgressMonitor(jobId)
						result = gitService.cloneOrUpdate(url, hookJobProgressMonitor)
						status = HookJob.HookJobStatus.COMPLETED
					} catch (HookJobException e) {
						status = HookJob.HookJobStatus.ERROR
						error = e.message
					}

					// save the job final state
					HookJob.withNewSession {
						def j = HookJob.get(jobId)
						j.status = status
						j.error = error
						j.result = result
						j.save(failOnError: true, flush: true)
					}

				} finally {
					persistenceInterceptor.flush()
					persistenceInterceptor.destroy()
				}
			}
		}
	}
	
	def cancel(id) {
		HookJob.withNewSession {
			def job = HookJob.get(id)
			job.status = HookJob.HookJobStatus.CANCELLED
			job.save(failOnError: true, flush: true)
		}
	}
	
}
