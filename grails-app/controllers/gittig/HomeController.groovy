package gittig

class HomeController {

	def pathService
	
	def gitService
	
	def springSecurityService
	
	def index() {
		def hooks = ['github', 'bitbucket', 'beanstalk']
		def repos = []
		if (springSecurityService.isLoggedIn()) {
			repos = pathService.listRepos().collect { path -> 
				def remote = gitService.getRemoteUrl(path)
				[path: path, remote: remote]
			}
			
		}
		[hooks: hooks, repos: repos]
	}
}
