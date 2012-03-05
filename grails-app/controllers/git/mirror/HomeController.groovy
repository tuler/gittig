package git.mirror

class HomeController {

	def pathService
	
	def index() {
		def hooks = ['github', 'bitbucket', 'beanstalk']
		def repos = pathService.listRepos()
		[hooks: hooks, repos: repos]
	}
}
