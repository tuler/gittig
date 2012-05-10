package gittig

import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.TextProgressMonitor

class HookJobProgressMonitor implements ProgressMonitor {
	
	private long jobId
	
	public HookJobProgressMonitor(long jobId) {
		this.jobId = jobId
	}
	
	void beginTask(String title, int totalWork) {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.title = title
			job.progress.completed = 0
			job.progress.totalWork = totalWork
			job.save(failOnError: true)
		}
	}
	
	void endTask() {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.save(failOnError: true)
		}
	}
	
	boolean isCancelled() {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			return job.status == HookJob.HookJobStatus.CANCELLED
		}
	}
	
	void start(int totalTasks) {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.totalTasks = totalTasks
			job.save(failOnError: true)
		}
	}
	
	void update(int completed) {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.completed += completed
			job.save(failOnError: true)
		}
	}
	
}