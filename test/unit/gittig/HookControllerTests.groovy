package gittig

import org.codehaus.groovy.grails.web.json.JSONObject
import grails.test.mixin.*
import org.junit.*

@TestFor(HookController)
class HookControllerTests {

	/**
	 * http://help.github.com/post-receive-hooks/
	 */
	void testGithub() {
		params.payload = """{
		  "before": "5aef35982fb2d34e9d9d4502f6ede1072793222d",
		  "repository": {
		    "url": "http://github.com/defunkt/github",
		    "name": "github",
		    "description": "You're lookin' at it.",
		    "watchers": 5,
		    "forks": 2,
		    "private": 1,
		    "owner": {
		      "email": "chris@ozmm.org",
		      "name": "defunkt"
		    }
		  },
		  "commits": [
		    {
		      "id": "41a212ee83ca127e3c8cf465891ab7216a705f59",
		      "url": "http://github.com/defunkt/github/commit/41a212ee83ca127e3c8cf465891ab7216a705f59",
		      "author": {
		        "email": "chris@ozmm.org",
		        "name": "Chris Wanstrath"
		      },
		      "message": "okay i give in",
		      "timestamp": "2008-02-15T14:57:17-08:00",
		      "added": ["filepath.rb"]
		    },
		    {
		      "id": "de8251ff97ee194a289832576287d6f8ad74e3d0",
		      "url": "http://github.com/defunkt/github/commit/de8251ff97ee194a289832576287d6f8ad74e3d0",
		      "author": {
		        "email": "chris@ozmm.org",
		        "name": "Chris Wanstrath"
		      },
		      "message": "update pricing a tad",
		      "timestamp": "2008-02-15T14:36:34-08:00"
		    }
		  ],
		  "after": "de8251ff97ee194a289832576287d6f8ad74e3d0",
		  "ref": "refs/heads/master"
		}"""

		def queueControl = mockFor(QueueService)
		queueControl.demand.enqueue(1..1) { u -> 
			assert u == "http://github.com/defunkt/github"
		}
		controller.queueService = queueControl.createMock()
		controller.github()
	}

	/**
	 * http://confluence.atlassian.com/display/BITBUCKET/Setting+Up+the+bitbucket+POST+Service
	 */
	void _testBitbucket() {
		params.payload = """{
		    'service': {
		        'url': 'http://some.domain/some/url'
		    },
		    'repository': {
		        'website': u 'http://bitheap.org/cram/',
		        'fork': False,
		        'name': u 'cram',
		        'scm': u 'hg',
		        'owner': u 'brodie',
		        'absolute_url': u '/brodie/cram/',
		        'slug': u 'cram',
		        'is_private': False
		    },
		    'commits': [{
		        'node': u '5c707ddc0838',
		        'files': [{
		            'type': 'modified',
		            'file': u 'setup.py'
		        }],
		        'branch': u 'default',
		        'utctimestamp': u '2011-04-15 06:53:52+00:00',
		        'timestamp': u '2011-04-15 08:53:52',
		        'raw_node': u '5c707ddc083814301b0062e88425e371bbeade0f',
		        'message': u 'setup.py: satisfy my obsessive desire for alpha-sorted lists',
		        'size': -1,
		        'author': u 'brodie',
		        'parents': [u '877408e570d7'],
		        'raw_author': u 'Brodie Rao <brodie@some.domain>',
		        'revision': 206
		    }, {
		        'node': u '8f15beaf3a8d',
		        'files': [{
		            'type': 'modified',
		            'file': u 'cram.py'
		        }, {
		            'type': 'modified',
		            'file': u 'examples/test.t'
		        }],
		        'branch': u 'default',
		        'utctimestamp': u '2011-04-15 06:54:17+00:00',
		        'timestamp': u '2011-04-15 08:54:17',
		        'raw_node': u '8f15beaf3a8d1ec0ccd127adaa026e7c1d2ab200',
		        'message': u "runner: prevent Python's SIGPIPE handler from being inherited by subprocesses\n\nThis fixes issue #16 that reports getting broken pipe errors for this\ntest:\n\n  \$ cat /dev/urandom | head -1 > /dev/null\n  cat: write error: Broken pipe",
		        'size': -1,
		        'author': u 'brodie',
		        'parents': [u '5c707ddc0838'],
		        'raw_author': u 'Brodie Rao <brodie@some.domain>',
		        'revision': 207
		    }],
		    'broker': 'post',
		    'canon_url': 'https://bitbucket.org',
		    'user': 'brodie'
		}"""

		def queueControl = mockFor(QueueService)
		queueControl.demand.enqueue(1..1) { u -> 
			assert u == "https://bitbucket.org/brodie/cram/"
		}
		controller.queueService = queueControl.createMock()
		controller.bitbucket()
	}

	/**
	 * http://support.beanstalkapp.com/customer/portal/articles/68110-trigger-a-url-on-commit-with-web-hooks
	 */
	void testBeanstalk() {
		params.payload = """{
		    "before": "7295fb96e74d1b54ba65cb003dce4da4193b5d29",
		    "after": "4ec8a1fcb42d278b60b33baa403a85c2e6706b5f",
		    "push_is_too_large": false,
		    "ref": "refs/heads/development",
		    "branch": "development",
		    "uri": "git@wildbit.beanstalkapp.com:/beanstalk.git",
		    "repository": {
		        "name": "beanstalk",
		        "owner": {
		          "email": "sdmitry@gmail.com",
		          "name": "Dima Sabanin"
		        },
		        "private": true,
		        "url": "http://wildbit.beanstalkapp.com/beanstalk"
		    },
		    "commits": [
		      {
		        "id": "4ec8a1fcb42d278b60b33baa403a85c2e6706b5f",
		        "author": {
		          "email": "sdmitry@gmail.com",
		          "name": "Dima Sabanin"
		        },
		        "changed_dirs": [],
		        "changed_files": [
		          [
		            "db/schema.rb",
		            "edit"
		          ]
		        ],
		        "url": "http://wildbit.beanstalkapp.com/beanstalk/changesets/4ec8a1fcb42d278b60b33baa403a85c2e6706b5f",
		        "id": "4ec8a1fcb42d278b60b33baa403a85c2e6706b5f",
		        "message": "Updated schema",
		        "timestamp": "2010-04-14T17:54:16+08:00"
		      },
		      {
		        "id": "7295fb96e74d1b54ba65cb003dce4da4193b5d29",
		        "author": {
		          "email": "sdmitry@gmail.com",
		          "name": "Dima Sabanin"
		        },
		        "changed_dirs": [],
		        "changed_files": [
		          [
		            "lib/hooks/git/git_adapter.rb",
		            "edit"
		          ]
		        ],
		        "url": "http://wildbit.beanstalkapp.com/beanstalk/changesets/7295fb96e74d1b54ba65cb003dce4da4193b5d29",
		        "id": "7295fb96e74d1b54ba65cb003dce4da4193b5d29",
		        "message": "Fixing 'Broken pipe' error",
		        "timestamp": "2010-04-15T17:54:16+08:00"
		      }
		    ]
		}"""

		def queueControl = mockFor(QueueService)
		queueControl.demand.enqueue(1..1) { u -> 
			assert u == "git@wildbit.beanstalkapp.com:/beanstalk.git"
		}
		controller.queueService = queueControl.createMock()
		controller.beanstalk()
	}

	/**
	 * http://support.atechmedia.com/codebase/docs/howtos/repository-push-commit-notifications
	 */
	void testCodebase() {
		params.payload = """{
		   "before":"fc10b3aa5a9e39ac326489805bba5c577f04db85",                                   // Commit ref before the push
		   "after":"840daf31f4f87cb5cafd295ef75de989095f415b",                                    // Commit ref after the push
		   "ref":"refs/heads/master",                                                             // Branch of the push
		   "repository":{
		      "name":"Git Repo #1",                                                               // Friendly repository name
		      "url":"http://test.codebasehq.com/projects/test-repositories/repositories/git1",    // URL to repository browser in Codebase
		      "clone_url":"git@codebasehq.com:test/test-repositories/git1.git",                   // Default clone URL
		      "clone_urls":{
		         "ssh":"git@codebasehq.com:test/test-repositories/git1.git",                      // SSH clone URL
		         "git":"git://codebasehq.com:test/test-repositories/git1.git",                    // git:// clone URL
		         "http":"https://test.codebasehq.com/test-repositories/git1.git"                  // HTTP clone URL
		      },
		      "project":{
		         "name":"Test Repositories",                                                      // Containing project friendly name
		         "url":"http://test.codebasehq.com/projects/test-repositories",                   // Containing project URL
		         "status":"active"                                                                // Containing project status
		      }
		   },
		   "user":{
		      "name":"Dan Wentworth",                                                             // Name of user who performed the push
		      "username":"dan",                                                                   // Username of person who performed push
		      "email":"dan@atechmedia.com"                                                        // Email address of person who performed push
		   },
		   "commits":[                                                                            // Array of commits in a push
		      {
		         "id":"840daf31f4f87cb5cafd295ef75de989095f415b",                                 // Ref of commit
		         "message":"Extra output for the rrrraaaagh",                                     // Commit message
		         "author":{
		            "name":"Dan Wentworth",                                                       // Commit author
		            "email":"dan@atechmedia.com"                                                  // Commit email address
		         },
		         "timestamp":"Mon, 18 Jul 2011 10:50:01 +0100",                                   // Date/Time of commit
		         "url":"http://test.codebasehq.com/projects/test-repositories/repositories/git1/commit/840daf31f4f87cb5cafd295ef75de989095f415b"
		      }
		   ]
		}"""

		def queueControl = mockFor(QueueService)
		queueControl.demand.enqueue(1..1) { u -> 
			assert u == "git@codebasehq.com:test/test-repositories/git1.git"
		}
		controller.queueService = queueControl.createMock()
		controller.codebase()
	}
}
