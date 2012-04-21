package git.mirror

import org.eclipse.jgit.lib.ProgressMonitor
import quartz.progressbar.QuartzProgressData


class HookJobProgressAdapter implements ProgressMonitor {
	
	HookJob job
	
    QuartzProgressData quartzProgressData
    
	private boolean cancelled
	
	private title
	
	private totalWork
	
	private totalTasks
	
	private completed
	
	HookJobProgressAdapter(HookJob job, QuartzProgressData quartzProgressData) {
		this.job = job
        this.quartzProgressData = quartzProgressData
	}
	
	void beginTask(String title, int totalWork) {
		this.title = title
		this.totalWork = totalWork
        quartzProgressData.msg = title
        quartzProgressData.total = totalWork
        println "title = ${title} totalWork = ${totalWork}"
	}
	
	void endTask() {
        
	}
	
	boolean isCancelled() {
		cancelled
	}
	
	void start(int totalTasks) {
        quartzProgressData.total = totalTasks
		this.totalTasks = totalTasks
	}
	
	void update(int completed) {
        quartzProgressData.step = completed
		this.completed = completed
	}
	
	void cancel() {
		cancelled = true
	}
	
	double progress() {
		(double) completed / totalTasks
	}
	
}