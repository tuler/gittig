import git.mirror.*

import org.codehaus.groovy.grails.support.PersistenceContextInterceptor
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BootStrap {

	PersistenceContextInterceptor persistenceInterceptor
	
	def queueService
	
	def pollingExecutor
	
	def queueExecutor
	
	def grailsApplication
	
	def init = { servletContext ->
		if (Role.count() == 0) {
			log.info "Creating standard roles"
			['ROLE_ADMIN', 'ROLE_USER'].each { role -> 
				new Role(authority: role).save(failOnError: true, flush: true)
			}
		}
		
		if (User.count() == 0) {
			log.info "Creating 'admin' user"
			def user = new User(username: 'admin', userRealName: 'admin', password: 'admin', enabled: true, email: 'admin@servername').save(failOnError: true)
			UserRole.create(user, Role.findByAuthority('ROLE_ADMIN'), true)
		}
		
		// polling scheduling
		def pollingInterval = grailsApplication.config.app.pollingInterval
		if (pollingInterval > 0) {
			pollingExecutor = Executors.newSingleThreadScheduledExecutor()
			pollingExecutor.scheduleWithFixedDelay({
		
			}, 10, pollingInterval, TimeUnit.MINUTES)
		}

		// dequeue scheduling
		def dequeueInterval = grailsApplication.config.app.dequeueInterval
		queueExecutor = Executors.newSingleThreadScheduledExecutor();
		queueExecutor.scheduleWithFixedDelay({
			persistenceInterceptor.init()
			try {
				queueService.dequeueAndRun()
			} finally {
				persistenceInterceptor.flush()
				persistenceInterceptor.destroy()
			}
		}, 10, dequeueInterval, TimeUnit.SECONDS)
		
	}
	def destroy = {
		if (pollingExecutor) {
			pollingExecutor.shutdown()
		}
		queueExecutor.shutdown()
	}
}
