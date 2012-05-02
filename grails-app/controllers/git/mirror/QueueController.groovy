package git.mirror

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

class QueueController {

	def hookJobService
	
	def hookJobExecutor
	
	@Secured(['ROLE_USER','ROLE_ADMIN'])
	def index() {
		[jobs: HookJob.list(params)]
	}

	def status() {
		render(contentType: "text/json") {
            [status: 'going']
		}
	}
	
	@Secured(['ROLE_USER','ROLE_ADMIN'])
	def enqueue(RepoCommand cmd) {
		// TODO: keep service name? use service name as remote name?
        log.debug("remote " + cmd.remote)
        log.debug("path " + cmd.path)
		hookJobService.enqueue(cmd.remote, cmd.path, 'undefined')
		redirect action: 'index'
	}
	
}
