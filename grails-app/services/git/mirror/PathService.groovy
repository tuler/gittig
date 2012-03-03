package git.mirror

class PathService {

    def resolvePath(url) {
		def configuration = Configuration.find {}
		if (configuration) {
			def locationResolverName = configuration.locationResolver
			def locationResolver = ctx.getBean(locationResolverName)
			
			// TODO
			def service = ''
			def username = ''
			def name = ''
			locationResolver.resolveLocation(service, username, name);
		}
    }
}
