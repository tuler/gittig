package git.mirror

class UsernameLocationResolver {
	
	String resolveLocation(String service, String username, String name) {
		return "/${username}/${name}.git"
	}
	
}