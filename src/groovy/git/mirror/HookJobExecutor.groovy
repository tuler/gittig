package git.mirror

class HookJobExecutor extends TimerTask {

	def hookJobService
	
	public void run() {
		def job = hookJobService.dequeue()
		if (job) {
			def progressMonitor = new HookJobProgress(job)
			try {
				gitService.cloneOrUpdate(job.url, job.path, progressMonitor)
				job.status = HookJob.HookJobStatus.COMPLETED
			} catch (e) {
				job.status = HookJob.HookJobStatus.ERROR
				job.error = e.message
			}
			job.save(failOnError: true)
		}
	}
	
}