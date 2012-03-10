package git.mirror

class SetupController {

	def gitService
	
	def index() {
		def configuration = Configuration.find {} ?: new Configuration()
		[configuration: configuration]
	}
	
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
