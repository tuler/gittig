package git.mirror

import grails.test.mixin.*
import org.junit.*

@TestFor(PathService)
class PathServiceTests {

	void testResolvePathGitHub() {
		assert "/tmp/git-mirror.git" == service.resolvePath("git@github.com:tuler/git-mirror.git")
	}
}
