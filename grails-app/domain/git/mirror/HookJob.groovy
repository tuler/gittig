package git.mirror

class HookJob {
	
	String url
	
	String path

	String hook
	
	HookJobStatus status = HookJobStatus.WAITING
	
	Date dateCreated
	
	Date lastUpdated
	
	static constraints = {
		url blank: false
		path blank: false
		hook blank: false
	}

	enum HookJobStatus {
		WAITING, 
		RUNNING, 
		DISCARDED, 
		COMPLETED
	}
}
