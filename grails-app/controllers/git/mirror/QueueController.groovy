package git.mirror

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

class QueueController {

	def queueService
	
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
		// TODO: keep service name? use service name as remote name?
        log.debug("Enqueuing path ${cmd.path} remote ${cmd.remote}")
		queueService.enqueue(cmd.remote, cmd.path, 'undefined')
		redirect action: 'index'
	}
	
	def dequeue() {
		log.debug "Manually dequeuing job"
		queueService.dequeueAndRun()
		redirect action: 'index'
	}
	
}
