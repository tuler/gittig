package git.mirror

class HookController {

	def pathService
	
	def gitService
	
	/**
	 * http://help.github.com/post-receive-hooks/
	 */
	def github() {
		def json = request.JSON
		log.debug(json)
		def url = json['repository']['url']
		
		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// clone or update
		gitService.cloneOrUpdate(url, path)
	}
	
	/**
	 * http://confluence.atlassian.com/display/BITBUCKET/Setting+Up+the+bitbucket+POST+Service
	 */
	def bitbucket() {
		def json = request.JSON

		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// clone or update
		gitService.cloneOrUpdate(url, path)
	}

	/**
	 * http://support.beanstalkapp.com/customer/portal/articles/68110-trigger-a-url-on-commit-with-web-hooks
	 */
    def beanstalk() {
		def json = request.JSON
		def url = json.get('uri')

		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// clone or update
		gitService.cloneOrUpdate(url, path)
	}
	
}
