package git.mirror

import org.springframework.context.*

class PathService implements ApplicationContextAware {

	ApplicationContext applicationContext
	
	def gitService

	/**
	 * This methods analyzes a repo url and extract three parts: 
	 * service: the service name, i.e. github or bitbucket or beanstalk
	 * username: the username of the repository (or company name in case of beanstalk)
	 * name: the repository (or project) name.
	 * 
	 * These are all examples of valid urls: 
	 * http://github.com/tuler/git-mirror
	 * git@github.com:tuler/git-mirror.git
	 * git@bitbucket.org:tuler/git-mirror.git
	 * git@tuler.beanstalkapp.com:/git-mirror.git
	 */
	def extractUrlParts(url) {
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
	
	/**
	 * This method resolves a local path of a remote url.
	 * The service name, user and repo name are extracted from the url, and then
	 * the configured locationResolver is used to define the local path.
	 */
	def resolvePath(url) {
		def configuration = Configuration.find {}
		if (configuration) {
			def locationResolverName = configuration.locationResolver
			def locationResolver = applicationContext.getBean(locationResolverName)
			def parts = extractUrlParts(url)
			def path = configuration.baseDir + locationResolver.resolveLocation(parts.service, parts.username, parts.name)
			log.debug "${url} resolved to path ${path}"
			return path
		}
    }

	/**
	 * This method scan the filesystem looking for git repositories.
	 * The scan is started in a base directory and go as deeply as the locationResolver
	 * configuration.
	 *
	 * Repository must be bare repos, and the directory name must end with ".git"
	 */
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
				def remote = gitService.getRemoteUrl(path)
				[path: path, remote: remote]
			}
		} else {
			return []
		}
	}

}
