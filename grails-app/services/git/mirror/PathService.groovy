package git.mirror

import org.springframework.context.*

class PathService implements ApplicationContextAware {

	ApplicationContext applicationContext

	def extractUrlParts(url) {
		// http://github.com/tuler/git-mirror
		// git@github.com:tuler/git-mirror.git
		// git@bitbucket.org:tuler/git-mirror.git
		// git@tuler.beanstalkapp.com:/git-mirror.git
		
		def services = [
			github: [~/git@github.com:([^\/]+)\/([^\.]+).git/, ~/https?:\/\/github.com\/([^\/]+)\/(.+)/], 
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
			def locationResolver = applicationContext.getBean(locationResolverName)
			def parts = extractUrlParts(url)
			def path = locationResolver.resolveLocation(parts.service, parts.username, parts.name)
			return configuration.baseDir + path
		}
    }

	def listRepos() {
		def configuration = Configuration.find {}
		if (configuration) {
			def depths = [
				'nameLocationResolver': 1, 
				'usernameLocationResolver': 2, 
				'serviceLocationResolver': 3
			]
			def maxdepth = depths[configuration.locationResolver]
			def baseDir = configuration.baseDir
			def cmd = "find ${baseDir} -type d -name *.git -maxdepth ${maxdepth}"
			return cmd.execute().text.readLines().collect { path -> 
				def remote = "git remote -v".execute(null, new File(path)).text
				[path: path, remote: remote]
			}
		} else {
			return []
		}
	}

}
