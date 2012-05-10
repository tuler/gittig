package gittig

import org.springframework.context.*

class ConfigService implements ApplicationContextAware {
	
	static transactional = false

	ApplicationContext applicationContext
	
	def grailsApplication
	
	def validate() {
		def errors = []
		def config = grailsApplication.config.app

		// base dir
		if (!config.baseDir) {
			errors << "config.error.no.baseDir"
		} else {
			def f = new File(config.baseDir)
			if (!f.exists()) {
				errors << "config.error.baseDir.not.exists"
			} else if (!f.canWrite()) {
				errors << "config.error.baseDir.not.writable"
			}			
		}

		// locationResolver
		if (!config.locationResolver) {
			errors << "config.error.no.locationResolver"
		} else {
			if (!applicationContext.getBean(config.locationResolver)) {
				errors << "config.error.locationResolver.invalid"
			}
		}

		return errors
	}
}