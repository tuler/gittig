package git.mirror

class HookJobProgress {
	
	String title
	
	int totalWork
	
	int totalTasks
	
	boolean completed
	
	boolean cancelled
	
	static belongsTo = [job: HookJob]

	public transient double getProgress() {
		if (totalTasks > 0) {
			return (double) completed / totalTasks
		} else {
			return 0.0
		}
	}
	
	static constraints = {
		title nullable: true
	}
}