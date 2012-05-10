package gittig

public interface LocationResolver {
	
	String resolveLocation(String service, String username, String name);
	
}