package gittig

import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.*
import org.junit.*

@TestFor(HomeController)
class HomeControllerTests {

	void testHooks() {
		// springSecurityService mock
		def springSecurityServiceControl = mockFor(SpringSecurityService)
		springSecurityServiceControl.demand.isLoggedIn(1..1) {
			true
		}
		controller.springSecurityService = springSecurityServiceControl.createMock()
		
		// pathService mock
		def pathServiceControl = mockFor(PathService)
		pathServiceControl.demand.listRepos(1..1) {
			[]
		}
		controller.pathService = pathServiceControl.createMock()

		// gitService mock
		def gitServiceControl = mockFor(GitService)
		gitServiceControl.demand.getRemoteUrl(0..0) {
			''
		}
		controller.gitService = gitServiceControl.createMock()
		
		assert ['github', 'bitbucket', 'beanstalk', 'codebase'] == controller.index().hooks
	}
	
}
