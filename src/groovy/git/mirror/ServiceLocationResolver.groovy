package git.mirror

class ServiceLocationResolver {
	
	String resolveLocation(String service, String username, String name) {
		return "/${service}/${username}/${name}.git"
	}
	
}