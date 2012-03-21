package git.mirror

class HookJob {
	
	String key
	
	String url
	
	String path

	String hook
	
	HookJobStatus status = HookJobStatus.WAITING
	
	String error
	
	Date dateCreated
	
	Date lastUpdated
	
	static constraints = {
		key blank: false
		url blank: false
		path blank: false
		hook blank: false
		error nullable: true
	}

	enum HookJobStatus {
		WAITING, 
		RUNNING, 
		DISCARDED, 
		ERROR, 
		COMPLETED
	}
	
	static waiting = where {
		status == HookJobStatus.WAITING
	}
}
