package gittig

class QueueService {

	static transactional = false
	
	def gitService
	
	def grailsApplication
	
	def list(params) {
		// list completed/cancelled/discarded/error jobs from this date on
		def queueTimeout = grailsApplication.config.app.queueTimeout
		def date = new Date(System.currentTimeMillis() - (1000 * queueTimeout))

		HookJob.withCriteria {
			or {
				eq('status', HookJob.HookJobStatus.WAITING)
				eq('status', HookJob.HookJobStatus.RUNNING)
				ge('dateCreated', date)
			}
		}
	}
	
	def enqueue(url) {
		new HookJob(url: url).save(failOnError: true, flush: true)
	}
	
	def dequeue() {
		HookJob.withNewSession {
			def waiting = HookJob.executeQuery "from HookJob j where j.status = ? and j.url not in (select jr.url from HookJob jr where jr.status = ?)", 
				[HookJob.HookJobStatus.WAITING, HookJob.HookJobStatus.RUNNING]
			if (waiting) {
				def job = waiting.first()
				log.info "Dequeuing job for ${job.url}"

				// initialize the progress and set to RUNNING status
				job.progress = new HookJobProgress(job: job)
				job.status = HookJob.HookJobStatus.RUNNING
				job.save(failOnError: true, flush: true)
				
				// discard other waiting jobs of the same url
				def discard = HookJob.withCriteria {
					eq('status', HookJob.HookJobStatus.WAITING)
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
			log.info "Running job for ${job.url}"

			// run the job
			def status
			def error
			def result
			try {
				def hookJobProgressMonitor = new HookJobProgressMonitor(job.id)
				result = gitService.cloneOrUpdate(job.url, hookJobProgressMonitor)
				status = HookJob.HookJobStatus.COMPLETED
			} catch (HookJobException e) {
				status = HookJob.HookJobStatus.ERROR
				error = e.message
			}
			
			// save the job final state
			HookJob.withNewSession {
				def j = HookJob.get(job.id)
				j.status = status
				j.error = error
				j.result = result
				j.save(failOnError: true, flush: true)
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
