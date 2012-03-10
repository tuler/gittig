package git.mirror

class ConfigurationFilters {

	def filters = {
		all(controller:'*', action:'*') {
			before = {
				if (!(controllerName in ['setup', 'login'])) {
					Configuration configuration = Configuration.find {}
					if (!configuration) {
						redirect controller: 'setup', action: 'index'
					}
				}
			}
			after = { Map model ->

			}
			afterView = { Exception e ->

			}
		}
	}
}
