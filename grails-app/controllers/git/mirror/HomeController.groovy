package git.mirror

class HomeController {

	def index() {
		def hooks = ['github', 'bitbucket', 'beanstalk']
		def repos = []
		[hooks: hooks, repos: repos]
	}
}
