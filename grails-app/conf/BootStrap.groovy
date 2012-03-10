import git.mirror.*

class BootStrap {

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
		
	}
	def destroy = {
	}
}
