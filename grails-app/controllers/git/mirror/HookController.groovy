package git.mirror

import org.codehaus.groovy.grails.web.json.JSONObject

class HookController {

	def queueService
	
	/**
	 * http://help.github.com/post-receive-hooks/
	 */
	def github() {
		def json = new JSONObject(params.payload)
		def url = json['repository']['url']
		log.debug "GitHub hook for ${url}"
		
		// TODO: check if json['repository']['private'] == 1 and switch to ssh, or it won't work
		
		// enqueue job for async processing
		queueService.enqueue(url)
		
		render "ok"
	}
	
	/**
	 * http://confluence.atlassian.com/display/BITBUCKET/Setting+Up+the+bitbucket+POST+Service
	 */
	def bitbucket() {
		def json = new JSONObject(params.payload)
		def url = json['repository']['absolute_url']
		log.debug "Bitbucket hook for ${url}"

		// enqueue job for async processing
		queueService.enqueue(url)
		
		render "ok"
	}

	/**
	 * http://support.beanstalkapp.com/customer/portal/articles/68110-trigger-a-url-on-commit-with-web-hooks
	 */
    def beanstalk() {
		def json = new JSONObject(params.payload)
		def url = json.get('uri')
		log.debug "Beanstalk hook for ${url}"

		// enqueue job for async processing
		queueService.enqueue(url)
		
		render "ok"
	}
	
}
