package gittig

import org.eclipse.jgit.api.*
import org.eclipse.jgit.api.errors.*
import org.eclipse.jgit.lib.*
import org.eclipse.jgit.transport.FetchResult

class GitService {

	static transactional = false

	def pathService
	
	/**
	 * Check if repository exists and updates, clone otherwise.
	 */
	def cloneOrUpdate(url, progressMonitor) {
		// resolve local git repo path
		log.debug "Cloning or updating ${url}"
		def path = pathService.resolvePath(url)
		if (path) {
			log.debug "Cloning or updating ${url} at ${path}"
			if (new File(path).exists()) {
				def result = update(path, progressMonitor)
				// TODO: how do I get a nice text output from a FetchResult?
				return result.messages
			} else {
				clone(url, path, progressMonitor)
				return ""
			}
		} else {
			log.error "Could not resolve path for url ${url}"
		}
	}
	
	/**
	 * Create a mirror clone of the url at the path
	 */
    def clone(url, path, progressMonitor) {
		if (url.startsWith('http') && !(url.endsWith('.git'))) {
			// XXX: JGit requires a .git at the end when url is http. Check this.
			url = url + '.git'
		}
		log.debug "Cloning ${url} at ${path}"
		try {
			Git.cloneRepository()
				.setURI(url)
				.setDirectory(new File(path))
				.setBare(true)
				.setCloneAllBranches(true)
				.setNoCheckout(true)
				.setProgressMonitor(progressMonitor)
				.call()
			log.debug "Done cloning ${url} at ${path}"
		} catch (JGitInternalException e) {
			log.error e.cause
			throw new HookJobException(e.cause.message)
		} catch (GitAPIException e) {
			log.error e
			throw new HookJobException(e.cause.message)
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
			FetchResult result = git.fetch()
				.setRemoveDeletedRefs(true)
				.setProgressMonitor(progressMonitor)
				.call()
			log.debug "Done updating ${path}"
			return result
		} catch (JGitInternalException e) {
			log.error e.cause
			throw new HookJobException(e.cause.message)
		} catch (GitAPIException e) {
			log.error e
			throw new HookJobException(e.message)
		} catch (IOException e) {
			log.error e
			throw new HookJobException(e.message)
		}
	}
	
	/**
	 * Get the url of the origin remote
	 */
	def getRemoteUrl(path) {
		RepositoryBuilder builder = new RepositoryBuilder()
		Repository repository = builder.setGitDir(new File(path)).readEnvironment().build()
		repository.config.getString('remote', 'origin', 'url')
	}
	
}
