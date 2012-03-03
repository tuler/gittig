class UrlMappings {

	static mappings = {
		"/setup" (controller: 'setup', action: 'index')
		"/$action" (controller: 'hook')
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
