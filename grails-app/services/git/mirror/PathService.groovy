package git.mirror

class PathService {

	def extractUrlParts(url) {
		// http://github.com/tuler/git-mirror
		// git@github.com:tuler/git-mirror.git
		// git@bitbucket.org:tuler/git-mirror.git
		// git@tuler.beanstalkapp.com:/git-mirror.git
		
		def services = [
			github: [~/git@github.com:([^\/]+)\/([^\.]+).git/, ~/http:\/\/github.com\/([^\/]+)\/(.+)/], 
			bitbucket: [~/git@bitbucket.org:([^\/]+)\/([^\.]+).git/], 
			beanstalk: [~/git@([^\.]+).beanstalkapp.com:\/([^\.]+).git/]
		]
		
		services.findResult { service, patterns -> 
			patterns.findResult { pattern -> 
				def matcher = url =~ pattern
				if (matcher.matches()) {
					return [service: service, username: matcher[0][1], name: matcher[0][2]]
				}
			}
		} ?: [:]
	}
	
	def resolvePath(url) {
		def configuration = Configuration.find {}
		if (configuration) {
			def locationResolverName = configuration.locationResolver
			def locationResolver = ctx.getBean(locationResolverName)
			def parts = extractUrlParts(url)
			locationResolver.resolveLocation(parts.service, parts.username, parts.name);
		}
    }

}
