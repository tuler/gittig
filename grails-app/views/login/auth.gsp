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

		<form action="${postUrl}" method="POST" id="loginForm" autocomplete="off">
			<fieldset>
				<div class="control-group">
					<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
					<div class="controls">
						<input type='text' class='text_' name='j_username' id='username'/>
					</div>
				</div>
				<div class="control-group">
					<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
					<div class="controls">
						<input type='password' class='text_' name='j_password' id='password'/>
					</div>
				</div>
				<div class="control-group">
					<label for='remember_me' class="checkbox"></label>
					<div class="controls">
						<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
						<g:message code="springSecurity.login.remember.me.label"/>
					</div>
				</div>
				<div class="form-actions">
					<input class="btn btn-primary" type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
				</div>
			</fieldset>
		</form>
		<r:script>
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		</r:script>
	</body>
</html>
