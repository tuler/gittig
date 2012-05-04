package git.mirror

import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.TextProgressMonitor

class HookJobProgressMonitor implements ProgressMonitor {
	
	private long jobId
	
	private StringWriter logWriter
	
	private TextProgressMonitor logProgressMonitor
	
	public HookJobProgressMonitor(long jobId) {
		this.logWriter = new StringWriter()
		this.logProgressMonitor = new TextProgressMonitor(logWriter)
		this.jobId = jobId
	}
	
	void beginTask(String title, int totalWork) {
		logProgressMonitor.beginTask(title, totalWork)
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.title = title
			job.progress.totalWork = totalWork
			job.log = logWriter.toString()
			job.save(failOnError: true)
		}
	}
	
	void endTask() {
		logProgressMonitor.endTask()
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.log = logWriter.toString()
			job.save(failOnError: true)
		}
	}
	
	boolean isCancelled() {
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			return job.cancelled
		}
	}
	
	void start(int totalTasks) {
		logProgressMonitor.start(totalTasks)
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.totalTasks = totalTasks
			job.log = logWriter.toString()
			job.save(failOnError: true)
		}
	}
	
	void update(int completed) {
		logProgressMonitor.update(completed)
		HookJob.withNewSession {
			def job = HookJob.get(jobId)
			job.progress.completed = completed
			job.log = logWriter.toString()
			job.save(failOnError: true)
		}
	}
	
}