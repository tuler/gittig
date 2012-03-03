package git.mirror

public interface LocationResolver {
	
	String resolveLocation(String service, String username, String name);
	
}