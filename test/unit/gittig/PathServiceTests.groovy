package gittig

import grails.test.mixin.*
import org.junit.*

@TestFor(PathService)
class PathServiceTests {

	void testExtractUrlPartsGithub() {
		def parts = service.extractUrlParts("git@github.com:tuler/gittig.git")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}

	void testExtractUrlPartsGithubHttp() {
		def parts = service.extractUrlParts("http://github.com/tuler/gittig")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}

	void testExtractUrlPartsGithubHttps() {
		def parts = service.extractUrlParts("https://github.com/tuler/gittig")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}

	void testExtractUrlPartsGithubHttpJGit() {
		def parts = service.extractUrlParts("https://github.com/tuler/gittig.git")
		assert "github" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}

	void testExtractUrlPartsBitbucket() {
		def parts = service.extractUrlParts("git@bitbucket.org:tuler/gittig.git")
		assert "bitbucket" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}

	void testExtractUrlPartsBeanstalk() {
		def parts = service.extractUrlParts("git@tuler.beanstalkapp.com:/gittig.git")
		assert "beanstalk" == parts.service
		assert "tuler" == parts.username
		assert "gittig" == parts.name
	}
	
}
