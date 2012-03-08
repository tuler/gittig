package git.mirror

import grails.test.mixin.*
import org.junit.*

@TestFor(HomeController)
class HomeControllerTests {

	void testHooks() {
		def pathServiceControl = mockFor(PathService)
		pathServiceControl.demand.listRepos(1..1) {
			[]
		}
		controller.pathService = pathServiceControl.createMock()
		assert ['github', 'bitbucket', 'beanstalk'] == controller.index().hooks
	}
	
}
