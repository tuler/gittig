package git.mirror

import org.quartz.*
import grails.plugin.quartz2.*
import org.springframework.beans.factory.InitializingBean

class HookJobService implements InitializingBean, JobListener {

	def quartzScheduler
	
	void afterPropertiesSet() throws Exception {
		// register myself as job listener
		quartzScheduler.listenerManager.addJobListener(this, null)
	}
	
	public String getName() {
		return "HookJob"
	}
	
	public void jobExecutionVetoed(JobExecutionContext context) {
	}
	
	public void jobToBeExecuted(JobExecutionContext context) {
		log.debug "jobToBeExecuted: ${context.jobDetail.jobDataMap.path} <- ${context.jobDetail.jobDataMap.url}"
	}
	
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.debug "jobWasExecuted: ${context.jobDetail.jobDataMap.path} <- ${context.jobDetail.jobDataMap.url}"
	}
	
	def enqueue(url, path, hook) {
		// new HookJob(url: url, path: path, hook: hook).save(failOnError: true)
		def trigger = TriggerBuilder.newTrigger()
			.startNow()
			.build()
		def jobDetail = new SimpleJobDetail(url, HookJob2.class, [url: url, path: path])
		quartzScheduler.scheduleJob(jobDetail, trigger)
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
