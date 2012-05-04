package git.mirror

import org.codehaus.groovy.grails.web.json.JSONObject

class HookController {

	def pathService
	
	def gitService
	
	def queueService
	
	/**
	 * http://help.github.com/post-receive-hooks/
	 */
	def github() {
		def json = new JSONObject(params.payload)
		def url = json['repository']['url']
		log.debug "GitHub hook for ${url}"
		
		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// enqueue job for async processing
		queueService.enqueue(url, path, 'github')
		
		render "ok"
	}
	
	/**
	 * http://confluence.atlassian.com/display/BITBUCKET/Setting+Up+the+bitbucket+POST+Service
	 */
	def bitbucket() {
		def json = request.JSON

		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// enqueue job for async processing
		queueService.enqueue(url, path, 'bitbucket')
		
		render "ok"
	}

	/**
	 * http://support.beanstalkapp.com/customer/portal/articles/68110-trigger-a-url-on-commit-with-web-hooks
	 */
    def beanstalk() {
		def json = new JSONObject(params.payload)
		def url = json.get('uri')
		log.debug "Beanstalk hook for ${url}"

		// resolve local git repo path
		def path = pathService.resolvePath(url)
		
		// enqueue job for async processing
		queueService.enqueue(url, path, 'beanstalk')
		
		render "ok"
	}
	
}
