package git.mirror

class HookJobService {

	def enqueue(url, path, hook) {
		new HookJob(url: url, path: path, hook: hook).save(failOnError: true)
	}
	
	/**
	 * Among the WAITING jobs, keep just one per url/path, mark the duplicates as DISCARDED
	 */
	def prune() {
		// TODO
	}
}
