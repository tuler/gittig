import git.mirror.*

import org.codehaus.groovy.grails.support.PersistenceContextInterceptor
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BootStrap {

	PersistenceContextInterceptor persistenceInterceptor
	
	def grailsApplication
	
	def queueService
	
	def queueExecutor
	
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
		
		// configuration validation
		def baseDir = grailsApplication.config.application.baseDir
		if (!baseDir) {
			log.error "No baseDir configuration!"
		} else {
			def f = new File(baseDir)
			if (!f.exists()) {
				log.error "Configured baseDir ${baseDir} is not readable"
			}
			if (!f.canWrite()) {
				log.error "Configured baseDir ${baseDir} is not writable"
			}			
		}

		def locationResolverName = grailsApplication.config.application.locationResolver
		if (!(locationResolverName in ['nameLocationResolver', 'usernameLocationResolver', 'serviceLocationResolver'])) {
			log.error "Configured locationResolver ${locationResolverName} is not valid"
		}
		
		// dequeue scheduling
		queueExecutor = Executors.newSingleThreadScheduledExecutor();
		queueExecutor.scheduleWithFixedDelay({
			persistenceInterceptor.init()
			try {
				queueService.dequeueAndRun()
			} finally {
				persistenceInterceptor.flush()
				persistenceInterceptor.destroy()
			}
		}, 10, 5, TimeUnit.SECONDS)
		
	}
	def destroy = {
		queueExecutor.shutdown()
	}
}
