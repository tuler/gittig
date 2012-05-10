gittig
======

gittig is a web application that manages local mirrors of remote git repositories.
gittig can be configured to do cron-like updates, but also provides out-of-the-box hook support for 3 popular git hosting providers: [GitHub](https://github.com), [BitBucket](https://bitbucket.org) and [Beanstalk](http://beanstalkapp.com).

Local mirrors of remote git repositories are useful for several reasons:

* backup
* faster read-only remotes (network latency)
* provide access to applications that require local git repositories (like Redmine)

Installation
------------

gittig is a Java application and can be deployed in any Java servlet container, and it was tested with [Tomcat](http://tomcat.apache.org).
You can use the pre-compiled [war file](https://github.com/downloads/tuler/gittig/gittig-1.0.war).

If you want to build from source, the application is written in [Grails](http://grails.org).
Grails provides a complete web stack, built on top of proven Java technologies: Groovy, Spring and Hibernate.
To generate the war file just issue a 'grails war' command.

Configuration
-------------

The only required configuration is the base local directory that gittig will use to store the repositories.
Create a file called '~/.gittig/gittig-config.properties' and define one or more than one of the configurations below:

<table>
	<tr>
		<th>Name</th>
		</th>Default Value</th>
		</th>Description</th>
	</tr>
	<tr>
		<td>app.baseDir</td>
		<td>none (required)</td>
		<td>The base local directory for the repositoreis, must be writable.</td>
	</tr>
	<tr>
		<td>app.locationResolver</td>
		<td>nameLocationResolver</td>
		<td>The strategy to resolve the local path of remote repositories. Valid values are 'nameLocationResolver', which is a flat structure, 'usernameLocationResolver', which is a 2-level structure using the repository username, and 'serviceLocationResolver', which is a 3-level structure which also uses the service name, like 'github' or 'bitbucket'. This is usefull if you are mirroring repositories from multiple users or hosting provider, so the local paths don't clash.</td>
	</tr>
	<tr>
		<td>app.queueTimeout</td>
		<td>60</td>
		<td>The number of seconds to keep completed jobs in the queue.</td>
	</tr>
	<tr>
		<td>app.pollingInterval</td>
		<td>0 (disabled)</td>
		<td>The polling interval for a cron-like update mode. Zero disables this mode.</td>
	</tr>
</table>

If you will use the hook support you will also need to configure the property 'grails.serverURL' with the absolute URL of your application, which must be reachable from the internet.

Security
--------

gittig uses [spring security](http://grails-plugins.github.com/grails-spring-security-core/docs/manual/) for its authentication.
As such it can be configured with a wide range of authentication methods, like LDAP.

TODO

SSH Keys
--------

gittig does not manage the authentication to the remote repositories, it uses plain git authentication process.
So you must have the ssh keys required to access the mirrored repositories.
The keys must not use passphrases, because the update process must be headless.

Hook Setup
----------

Each git hosting provider provides an instruction page about how to configure a hook. Gittig provides a hook URL for each supported provider.
Your deployment must be open to the Internet, so one of these providers can reach your server.

### GitHub

How to your project admin page, click on 'Service Hooks', select 'Post-Receive URLs' and add the URL provided at the home page of your gittig application.

![admin page](http://img.skitch.com/20100620-r8st7468q7q5waf3y85hmpwtqs.png "Admin Page")
![hook page](http://img.skitch.com/20100620-br6dw5iiyk2643fahkqbi54h36.png "Hook Page")

For further instructions check [GitHub documentation](http://help.github.com/post-receive-hooks/)

### BitBucket

Go to the Bitbucket **Admin** tab.
Click **Services** in the Additional options/settings section on the right-hand side of the screen.
Select the **POST** service from the dropdown list in the **Services Administration** section.
Click **Add service**.
Enter the URL provided at the home page of your gittig application.

For further instructions check [Bitbucket documentation](http://confluence.atlassian.com/display/BITBUCKET/Setting+Up+the+bitbucket+POST+Service)

### Beanstalk

In your repository, click **Setup** -> **Integration** -> **Web hooks**
Enter the URL provided at the home page of your gittig application.

For further instructions check [Beanstalk documentation](http://support.beanstalkapp.com/customer/portal/articles/68110-trigger-a-url-on-commit-with-web-hooks)
