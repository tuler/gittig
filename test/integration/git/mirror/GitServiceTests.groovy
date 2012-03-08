package git.mirror

import static org.junit.Assert.*
import org.junit.*
import org.junit.rules.*
import grails.test.mixin.*

@TestFor(GitService)
class GitServiceTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
    @Before
	void setUp() {
        // Setup logic here
	}

    @After
	void tearDown() {
        // Tear down logic here
	}

	@Rule
    @Test
	void testCloneThenUpdate() {
		def url = "http://github.com/tuler/git-mirror"
		def path = folder.root
		service.clone(url, path)
		assert ['git-mirror'] == path.list()
    }
}
