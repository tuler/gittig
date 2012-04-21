package git.mirror

import org.eclipse.jgit.lib.ProgressMonitor

class HookJobProgress implements ProgressMonitor {
	
	HookJob job
    
	private boolean cancelled
	
	private title
	
	private totalWork
	
	private totalTasks
	
	private completed
	
	HookJobProgress(HookJob job) {
		this.job = job
	}
	
	void beginTask(String title, int totalWork) {
		this.title = title
		this.totalWork = totalWork
	}
	
	void endTask() {
	}
	
	boolean isCancelled() {
		cancelled
	}
	
	void start(int totalTasks) {
		this.totalTasks = totalTasks
	}
	
	void update(int completed) {
        println "completed <- ${completed}"
		this.completed = completed
	}
	
	void cancel() {
		cancelled = true
	}
	
	double progress() {
		(double) completed / totalTasks
	}
	
}