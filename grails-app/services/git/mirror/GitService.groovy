package git.mirror

class GitService {

	/**
	 * Check if repository exists and updates, clone otherwise.
	 */
	def cloneOrUpdate(url, path) {
		if (new File(path).exists()) {
			update(path)
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
			def output = cmd.execute().text
			log.debug output
			return output
		}
    }

	/**
	 * Fetch the git repo at the given path
	 */
	def update(path) {
		runAsync {
			def cmd = "git remote update"
			log.debug "cd ${path}; ${cmd}"
			def output = cmd.execute(null, new File(path)).text
			log.debug output
			return output
		}
	}
	
	def getVersion() {
		def output = "git --version".execute().text
		def matcher = output =~ /git version ([\d\.]+)/
		return matcher.size() > 0 ? matcher[0][1] : null
	}
}
