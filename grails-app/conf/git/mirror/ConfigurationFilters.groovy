package git.mirror

class ConfigurationFilters {

	def configService
	
	def filters = {
		all(controller:'*', action:'*') {
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
