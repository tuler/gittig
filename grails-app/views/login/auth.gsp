<html>
	<head>
		<meta name='layout' content='main' />
		<title><g:message code="springSecurity.login.title"/></title>
	</head>
	<body>
		<h1><g:message code="springSecurity.login.header"/></h1>

		<g:if test='${flash.message}'>
			<div class='alert alert-error'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
			<input type='text' class='text_' name='j_username' id='username'/>
			<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
			<input type='password' class='text_' name='j_password' id='password'/>

			<label for='remember_me' class="checkbox">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<g:message code="springSecurity.login.remember.me.label"/>
			</label>

			<input class="btn" type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
		</form>
	<script type='text/javascript'>
		<!--
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		// -->
	</script>
	</body>
</html>
