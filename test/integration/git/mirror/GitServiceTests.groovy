package git.mirror

import static org.junit.Assert.*
import org.junit.*
import org.junit.rules.*
import grails.test.mixin.*

@TestFor(GitService)
class GitServiceTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
    @Test
	void testCloneThenUpdate() {
/*		def url = "http://github.com/tuler/git-mirror"
		def path = folder.root
		service.clone(url, path)
		assert ['git-mirror'] == path.list()*/
    }
}
