package gittig

class ConfigurationFilters {

	def configService
	
	def filters = {
		all(controller: 'home', action: '*') {
			before = {
				// get configuration errors
				request.configErrors = configService.validate()
			}

			after = { Map model ->
			}

			afterView = { Exception e ->
			}
		}
	}
}
