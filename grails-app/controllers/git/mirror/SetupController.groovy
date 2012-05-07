package git.mirror

import grails.plugins.springsecurity.Secured

class SetupController {

	def gitService
	
	@Secured(['ROLE_ADMIN', 'ROLE_ADMINISTRATORS'])
	def index() {
		def configuration = Configuration.find {} ?: new Configuration()
		[configuration: configuration]
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_ADMINISTRATORS'])
	def save() {
		def configuration = Configuration.find {} ?: new Configuration()
		configuration.properties = params
		if (!configuration.save()) {
            //TODO: show error msg
			render view: 'index', model: [configuration: configuration]
		} else {
			redirect url: '/'		
		}
	}
}
