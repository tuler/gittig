package git.mirror

class HookJobExecutor extends TimerTask {

	def queueService
	
	public void run() {
		// get a job and run it
		queueService.dequeueAndRun()
	}
	
}