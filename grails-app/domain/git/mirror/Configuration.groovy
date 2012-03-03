package git.mirror

class Configuration {

	String gitVersion
	
	String baseDir
	
	String locationResolver
	
    static constraints = {
		gitVersion blank: false
		baseDir validator: { val, obj -> 
			def f = new File(val)
			if (!f.exists()) return "not.exists"
			if (!f.canWrite()) return "not.writable"
			return true
		}
		locationResolver inList: ["nameLocationResolver, usernameLocationResolver", "serviceLocationResolver"]
    }
}
