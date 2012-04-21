package git.mirror

import org.eclipse.jgit.api.*
import org.eclipse.jgit.api.errors.*
import org.eclipse.jgit.lib.*

class GitService {

	/**
	 * Check if repository exists and updates, clone otherwise.
	 */
	def cloneOrUpdate(url, path, progressMonitor) {
		log.debug "Cloning or updating ${url} at ${path}"
		if (new File(path).exists()) {
			update(path, progressMonitor)
		} else {
			clone(url, path, progressMonitor)
		}
	}
	
	/**
	 * Create a mirror clone of the url at the path
	 */
    def clone(url, path, progressMonitor) {
		log.debug "Cloning ${url} at ${path}"
		try {
			if (url.startsWith('http')) {
				// XXX: JGit requires a .git at the end when url is http. Check this.
				url = url + '.git'
			}
			Git.cloneRepository()
				.setURI(url)
				.setDirectory(new File(path))
				.setBare(true)
				.setCloneAllBranches(true)
				.setNoCheckout(true)
				.setProgressMonitor(progressMonitor).call()
			log.debug "Done cloning ${url} at ${path}"
		} catch (JGitInternalException e) {
			log.error e.cause
		}
    }

	/**
	 * Fetch the git repo at the given path
	 */
	def update(path, progressMonitor) {
		log.debug "Updating ${path}"
		try {
			RepositoryBuilder builder = new RepositoryBuilder()
			Repository repository = builder.setGitDir(new File(path)).readEnvironment().build()
			Git git = new Git(repository)
			git.fetch().setRemoveDeletedRefs(true).setProgressMonitor(progressMonitor).call()
			log.debug "Done updating ${path}"
		} catch (JGitInternalException e) {
			log.error e.cause
		} catch (InvalidRemoteException e) {
			log.error e
		} catch (IOException e) {
			log.error e
		}
	}
	
	def getRemoteUrl(path) {
		RepositoryBuilder builder = new RepositoryBuilder()
		Repository repository = builder.setGitDir(new File(path)).readEnvironment().build()
		repository.config.getString('remote', 'origin', 'url')
	}
	
}
