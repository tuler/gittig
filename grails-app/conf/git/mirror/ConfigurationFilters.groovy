package git.mirror

class ConfigurationFilters {

	def filters = {
		all(controller:'*', action:'*') {
			before = {
				// TODO: check baseDir and locationResolver configuration
				// and do something if configuration is not valid
			}
			after = { Map model ->

			}
			afterView = { Exception e ->

			}
		}
	}
}
