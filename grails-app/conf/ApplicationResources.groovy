modules = {
	underscore {
		resource url: 'js/underscore.js'
	}
	
	application {
		resource url: 'js/application.js'
	}

	queue {
		dependsOn 'jquery, underscore'
		resource url: 'js/queue.js'
	}
}
