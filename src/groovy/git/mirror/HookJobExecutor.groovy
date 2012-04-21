package git.mirror

class HookJobExecutor extends TimerTask {

	def hookJobService
	
	def runningJobs
	
	public void run() {
		println "Running executor"
		// XXX: take only one job by now
		def job = hookJobService.dequeue()
		if (job) {
			def progressMonitor = new HookJobProgress(job)
			runningJobs.add(progressMonitor)
			try {
				gitService.cloneOrUpdate(job.url, job.path, progressMonitor)
				job.status = HookJob.HookJobStatus.COMPLETED
			} catch (e) {
				job.status = HookJob.HookJobStatus.ERROR
				job.error = e.message
			}
			job.save()
			runningJobs.remove(progressMonitor)
		}
	}
	
	def status() {
		runningJobs
	}
}