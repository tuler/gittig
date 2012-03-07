package git.mirror

import org.eclipse.jgit.api.*
import org.eclipse.jgit.api.errors.*
import org.eclipse.jgit.lib.*

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
			try {
				def progressMonitor = new TextProgressMonitor()
				Git.cloneRepository().setUri(url).setDirectory(new File(path)).setBare(true).setCloneAllBranches(true).setNoCheckout(true).setProgressMonitor(progressMonitor).call()
			} catch (JGitInternalException e) {
				log.error e
			}
		}
    }

	/**
	 * Fetch the git repo at the given path
	 */
	def update(path) {
		runAsync {
			try {
				def git = Git.open(new File(path))
				git.fetch().setRemoveDeletedRefs(true).setProgressMonitor(progressMonitor).call()
			} catch (JGitInternalException e) {
				log.error e
			}
		}
	}
	
	def getVersion() {
		"jgit 1.3.0"
	}
}
