package git.mirror

import org.quartz.*
import org.eclipse.jgit.lib.TextProgressMonitor

class HookJob2 implements Job {
	
	String url
	
	String path
	
	def grailsApplication
	
	public HookJob2() { }
	
	void execute(JobExecutionContext jobCtx) {
		def gitService = grailsApplication.mainContext.gitService
		log.info "Executing job for ${path} <- ${url}"
        def progressData = jobCtx.mergedJobDataMap.get("quartzProgressData")
        progressData.id = jobCtx.mergedJobDataMap.get("hookJob").id
		def progressMonitor = new HookJobProgressAdapter(progressData)
		gitService.cloneOrUpdate(url, path, progressMonitor)
        progressData.msg = "Done."
	}
}