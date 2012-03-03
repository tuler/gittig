package git.mirror

class NameLocationResolver {
	
	String resolveLocation(String service, String username, String name) {
		return "/${name}.git"
	}
	
}