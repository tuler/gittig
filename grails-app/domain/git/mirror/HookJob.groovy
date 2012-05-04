package git.mirror

class HookJob {
	
	String url
	
	String path

	String hook
	
	HookJobStatus status = HookJobStatus.WAITING
	
	boolean cancelled
	
	String error
	
	String result
	
	Date dateCreated
	
	Date lastUpdated
    
	HookJobProgress progress
	
	static constraints = {
		url blank: false
		path blank: false
		hook blank: false
		error nullable: true
		result nullable: true
		progress nullable: true
	}

	static mapping = {
		error type: "text"
		result type: "text"
	}
	
	enum HookJobStatus {
		WAITING, 
		RUNNING, 
		DISCARDED, 
		ERROR, 
		CANCELLED, 
		COMPLETED
	}
	
	static waiting = where {
		status == HookJobStatus.WAITING
	}
}
