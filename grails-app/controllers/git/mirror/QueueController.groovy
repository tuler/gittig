package git.mirror

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

class QueueController {

	def queueService
	
	def pathService
	
	def gitService
	
	@Secured(['ROLE_USER','ROLE_ADMIN', 'ROLE_ADMINISTRATORS'])
	def index() {
		[jobs: queueService.list(params)]
	}

	def status() {
		def results = queueService.list(params)
		render(contentType: "text/json") {
			jobs = array {
				for (j in results) {
					job id: j.id, status: j.status.name(), error: j.error, result: j.result, title: j.progress?.title, totalWork: j.progress?.totalWork, totalTasks: j.progress?.totalTasks, completed: j.progress?.completed, progress: j.progress?.progress
				}
			}
		}
	}
	
	@Secured(['ROLE_USER','ROLE_ADMIN', 'ROLE_ADMINISTRATORS'])
	def enqueue(RepoCommand cmd) {
        log.debug("Enqueuing remote ${cmd.remote}")
		queueService.enqueue(cmd.remote)
		redirect action: 'index'
	}
	
	def enqueueAll() {
		// list all repos and enqueue each one
		pathService.listRepos().each { path -> 
			def remote = gitService.getRemoteUrl(path)
			if (remote) {
				queueService.enqueue(remote)
			} else {
				log.warn "Repository ${path} does not contain a origin remote"
			}
		}
		redirect action: 'index'
	}
	
	def dequeue() {
		log.debug "Manually dequeuing job"
		queueService.dequeueAndRun()
		redirect action: 'index'
	}
	
	def cancel() {
		log.debug "Canceling job ${params.id}"
		queueService.cancel(params.id)
		render "OK"
	}
	
}
