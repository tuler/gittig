package git.mirror

import grails.test.mixin.*
import org.junit.*

@TestFor(HomeController)
class HomeControllerTests {

	void testHooks() {
		assert ['github', 'bitbucket', 'beanstalk'] == controller.index().hooks
	}
	
	void testRepos() {
		assert [] == controller.index().repos
	}
}
