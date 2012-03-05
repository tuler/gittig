package git.mirror

class SetupController {

	def gitService
	
	def index() {
		def configuration = Configuration.find {} ?: new Configuration(gitVersion: gitService.getVersion())
		[configuration: configuration]
	}
}
