package git.mirror

class HomeController {

	def pathService
	
	def springSecurityService
	
	def index() {
		def hooks = ['github', 'bitbucket', 'beanstalk']
		def repos = []
		if (springSecurityService.isLoggedIn()) {
			repos = pathService.listRepos()
		}
		[hooks: hooks, repos: repos]
	}
}
