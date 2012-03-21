modules = {
	application {
		resource url: 'js/application.js'
	}

	queue {
		dependsOn 'jquery'
		resource url: 'js/queue.js'
	}
	
}