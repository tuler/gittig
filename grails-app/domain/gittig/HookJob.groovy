package gittig

class HookJob {
	
	String url
	
	HookJobStatus status = HookJobStatus.QUEUED
	
	String error
	
	String result
	
	Date dateCreated
	
	Date lastUpdated
    
	HookJobProgress progress
	
	static constraints = {
		url blank: false
		error nullable: true
		result nullable: true
		progress nullable: true
	}

	static mapping = {
		error type: "text"
		result type: "text"
	}
	
	enum HookJobStatus {
		QUEUED, 
		WAITING, 
		RUNNING, 
		DISCARDED, 
		ERROR, 
		CANCELLED, 
		COMPLETED
	}
	
}
