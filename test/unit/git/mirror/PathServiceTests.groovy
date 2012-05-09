package git.mirror

import grails.test.mixin.*
import org.junit.*

@TestFor(PathService)
class PathServiceTests {

	void testExtractUrlPartsGithub() {
		def parts = service.extractUrlParts("git@github.com:tuler/git-mirror.git")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}

	void testExtractUrlPartsGithubHttp() {
		def parts = service.extractUrlParts("http://github.com/tuler/git-mirror")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}

	void testExtractUrlPartsGithubHttps() {
		def parts = service.extractUrlParts("https://github.com/tuler/git-mirror")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}

	void testExtractUrlPartsGithubHttpJGit() {
		def parts = service.extractUrlParts("https://github.com/tuler/git-mirror.git")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}

	void testExtractUrlPartsBitbucket() {
		def parts = service.extractUrlParts("git@bitbucket.org:tuler/git-mirror.git")
		assert "bitbucket" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}

	void testExtractUrlPartsBeanstalk() {
		def parts = service.extractUrlParts("git@tuler.beanstalkapp.com:/git-mirror.git")
		assert "beanstalk" == parts.service
		assert "tuler" == parts.username
		assert "git-mirror" == parts.name
	}
	
}
