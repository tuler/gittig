package git.mirror

class GitService {

	/**
	 * Check if repository exists and updates, clone otherwise.
	 */
	def cloneOrUpdate(url, path) {
		if (new File(path).exists()) {
			fetch(path)
		} else {
			clone(url, path)
		}
	}
	
	/**
	 * Create a mirror clone of the url at the path
	 */
    def clone(url, path) {
		runAsync {
			def cmd = "git clone --mirror ${url} ${path}"
			log.debug cmd
			return cmd.execute().text
		}
    }

	/**
	 * Fetch the git repo at the given path
	 */
	def update(path) {
		runAsync {
			def cmd = "git remote update"
			log.debug "cd ${path}; ${cmd}"
			return cmd.execute(null, new File(path)).text
		}
	}
	
	def getVersion() {
		def output = "git --version".execute().text
		def matcher = output =~ /git version ([\d\.]+)/
		return matcher.size() > 0 ? matcher[0][1] : null
	}
}
