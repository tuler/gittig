package git.mirror

import grails.plugins.springsecurity.Secured

class SetupController {

	def gitService
	
	@Secured(['ROLE_ADMIN'])
	def index() {
		def configuration = Configuration.find {} ?: new Configuration()
		[configuration: configuration]
	}
	
	@Secured(['ROLE_ADMIN'])
	def save() {
		def configuration = Configuration.find {} ?: new Configuration()
		configuration.properties = params
		if (!configuration.save()) {
			render view: 'index', model: [configuration: configuration]
		} else {
			redirect url: '/'		
		}
	}
}
