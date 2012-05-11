<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="git mirror" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<r:require modules="application, bootstrap" />
		<g:layoutHead />
		<r:layoutResources />
		<style>
		body {
			padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
		}
		</style>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<g:link class="brand" controller="home">git mirror</g:link>
					<div class="nav-collapse">
						<ul class="nav">
							<sec:ifAnyGranted roles="ROLE_USER,ROLE_ADMIN,ROLE_ADMINISTRATORS"><li class="${controllerName == 'queue' ? 'active' : ''}"><g:link controller="queue">Queue</g:link></li></sec:ifAnyGranted>
						</ul>
						<ul class="nav pull-right">
							<sec:ifLoggedIn>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">
										<sec:username />
										<b class="caret"></b>
									</a>
									<ul class="dropdown-menu">
										<li><g:link controller="logout">Logout</g:link></li>
									</ul>
								</li>
							</sec:ifLoggedIn>
							<sec:ifNotLoggedIn>
								<li><g:link controller="login">Login</g:link></li>
							</sec:ifNotLoggedIn>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<g:each in="${configErrors}" var="error">
				<div class="alert alert-error"><g:message code="${error}" default="${error}" /></div>
			</g:each>
			<g:layoutBody />
			<footer><span><a href="http://documentup.com/tuler/gittig"><g:img file="gittig.png" width="60" /></a></span></footer>
		</div>
		<r:layoutResources />
	</body>
</html>
