package git.mirror

class SetupController {

	def index() {
		def configuration = Configuration.findOrCreateWhere()
		[configuration: configuration]
	}
}
