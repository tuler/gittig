package git.mirror

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

class QueueController {

	def queueService
	
	@Secured(['ROLE_USER','ROLE_ADMIN'])
	def index() {
		[jobs: queueService.list(params)]
	}

	def status() {
		def results = queueService.list(params)
		render(contentType: "text/json") {
			jobs = array {
				for (j in results) {
					job id: j.id, status: j.status.name(), error: j.error, log: j.log, title: j.progress?.title, progress: j.progress?.progress
				}
			}
		}
	}
	
	@Secured(['ROLE_USER','ROLE_ADMIN'])
	def enqueue(RepoCommand cmd) {
		// TODO: keep service name? use service name as remote name?
        log.debug("remote " + cmd.remote)
        log.debug("path " + cmd.path)
		queueService.enqueue(cmd.remote, cmd.path, 'undefined')
		redirect action: 'index'
	}
	
	def dequeue() {
		queueService.dequeueAndRun()
		redirect action: 'index'
	}
	
}
