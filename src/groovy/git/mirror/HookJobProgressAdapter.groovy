package git.mirror

import org.eclipse.jgit.lib.ProgressMonitor
import quartz.progressbar.QuartzProgressData


class HookJobProgressAdapter implements ProgressMonitor {
	
	HookJob job
	
    QuartzProgressData quartzProgressData
    
    boolean cancelled // WTF?
    
    HookJobProgressAdapter(QuartzProgressData quartzProgressData) {
        this.quartzProgressData = quartzProgressData
    }
    
	HookJobProgressAdapter(HookJob job, QuartzProgressData quartzProgressData) {
		this.job = job
        this.quartzProgressData = quartzProgressData
	}
	
	void beginTask(String title, int totalWork) {
        quartzProgressData.msg = title
        if(totalWork == 0){
            quartzProgressData.total = 100
        }else{
            quartzProgressData.total = totalWork
        }
        quartzProgressData.step = 0
	}
	
	void endTask() {
        
	}
	
	boolean isCancelled() {
		this.@cancelled // WTF?
	}
	
	void start(int totalTasks) {
        quartzProgressData.total = totalTasks
	}
	
	void update(int completed) {
        quartzProgressData.step += completed
	}
	
	void cancel() {
		cancelled = true // WTF?
	}
	
}