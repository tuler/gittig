package git.mirror

import org.quartz.*
import grails.plugin.quartz2.*
import org.springframework.beans.factory.InitializingBean

class HookJobService implements InitializingBean, JobListener, SchedulerListener {

	def quartzScheduler
	
	void afterPropertiesSet() throws Exception {
		// register myself as job listener
		quartzScheduler.listenerManager.addSchedulerListener(this)
		quartzScheduler.listenerManager.addJobListener(this, null)
	}
	
	public String getName() {
		return "HookJob"
	}
	
	/**
	 * SchedulerListener
	 */
	void jobAdded (JobDetail jobDetail) {
		def key = jobDetail.key.toString()
		def url = jobDetail.jobDataMap.url
		def path = jobDetail.jobDataMap.path
		def hook = jobDetail.jobDataMap.hook
		log.debug "jobAdded (${key}): ${path} <- ${url}"
		new HookJob(key: key, url: url, path: path, hook: hook).save(failOnError: true)
	}
	
	void jobDeleted (JobKey jobKey) {
		
	}
	
	void jobPaused (JobKey jobKey) {
		
	}
	
	void jobResumed (JobKey jobKey) {
		
	}
	
	void jobScheduled (Trigger trigger) {
		
	}
	
	void jobsPaused (String jobGroup) {
		
	}
	
	void jobsResumed (String jobGroup) {
		
	}
	
	void jobUnscheduled (TriggerKey triggerKey) {
		
	}
	
	void schedulerError (String msg, SchedulerException cause) {}
	void schedulerInStandbyMode () {}
	void schedulerShutdown () {}
	void schedulerShuttingdown () {}
	void schedulerStarted () {}
	void schedulingDataCleared () {}
	void triggerFinalized (Trigger trigger) {}
	void triggerPaused (TriggerKey triggerKey) {}
	void triggerResumed (TriggerKey triggerKey) {}
	void triggersPaused (String triggerGroup) {}
	void triggersResumed (String triggerGroup) {}
	
	/**
	 * JobListener
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
	}
	
	public void jobToBeExecuted(JobExecutionContext context) {
		def key = context.jobDetail.key.toString()
		log.debug "jobToBeExecuted (${key}): ${context.jobDetail.jobDataMap.path} <- ${context.jobDetail.jobDataMap.url}"
        HookJob.withNewTransaction{ t ->
    		def job = HookJob.findByKey(key)
    		job.status = HookJob.HookJobStatus.RUNNING
    		job.save()
            context.mergedJobDataMap.put('hookJob', job)
		}
	}
	
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		def key = context.jobDetail.key.toString()
		log.debug "jobWasExecuted (${key}): ${context.jobDetail.jobDataMap.path} <- ${context.jobDetail.jobDataMap.url}"
        HookJob.withNewTransaction{ t ->
    		def job = HookJob.findByKey(key)
    		job.status = HookJob.HookJobStatus.COMPLETED
    		job.save()
		}
	}
	
	
	def enqueue(url, path, hook) {
		// new HookJob(url: url, path: path, hook: hook).save(failOnError: true)
		def trigger = TriggerBuilder.newTrigger()
			.startNow()
			.build()
		def id = "${url}-${new Date().format('yyyyMMddHHmmss')}"
		def jobDetail = new SimpleJobDetail(id, HookJob2.class, [url: url, path: path, hook: hook])
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
