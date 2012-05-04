package git.mirror

class QueueService {

	static transactional = false
	
	def gitService
	
	def enqueue(url, path, hook) {
		new HookJob(url: url, path: path, hook: hook).save(failOnError: true)
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
				return job
			} else {
				log.debug "No job to dequeue"
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
				result = gitService.cloneOrUpdate(job.url, job.path, hookJobProgressMonitor)
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
				if (result) {
					j.log = j.log + result
				}
				j.save(failOnError: true, flush: true)
			}
		} else {
			log.debug "No job to run"
		}
	}
	
	/**
	 * Among the WAITING jobs, keep just one per url/path, mark the duplicates as DISCARDED
	 */
	def prune() {
		// TODO
	}
}
