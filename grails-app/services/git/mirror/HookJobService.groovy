package git.mirror

class HookJobService {

	def enqueue(url, path, hook) {
		new HookJob(url: url, path: path, hook: hook).save(failOnError: true)
	}
	
	def dequeue() {
		def waiting = HookJob.waiting.findAll()
		if (waiting) {
			def job = waiting.first()
			log.info "Dequeuing job for ${job.url}"
			job.status = HookJob.HookJobStatus.RUNNING
			job.save()
		}
	}
	
	/**
	 * Among the WAITING jobs, keep just one per url/path, mark the duplicates as DISCARDED
	 */
	def prune() {
		// TODO
	}
}
