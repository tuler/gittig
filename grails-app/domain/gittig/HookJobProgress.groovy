package gittig

class HookJobProgress {
	
	String title
	
	int totalWork
	
	int totalTasks
	
	int completed
	
	static belongsTo = [job: HookJob]

	public transient double getProgress() {
		if (totalWork > 0) {
			return (double) completed / totalWork
		} else {
			return 0.0
		}
	}
	
	static constraints = {
		title nullable: true
	}
}