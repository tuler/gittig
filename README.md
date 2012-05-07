Gittig
======

Gittig is a web application that manages mirrors of remote git repositories.
Gittig provides out-of-the-box hook support for 3 popular git hosting providers: [GitHub](https://github.com), [BitBucket](https://bitbucket.org) and [Beanstalk](http://beanstalkapp.com).

Local mirrors of remote git repositories are useful for several reasons:

* backup
* network latency
* applications that require local git repositories (like Redmine)

Installation
------------

Gittig is an application written in [Grails](http://grails.org). Grails provides a complete web stack, built on top of proven Java technologies: Groovy, Spring and Hibernate.

TODO

Configuration
-------------

TODO

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
