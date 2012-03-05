<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
   </head>
	<body>
		<h1>Setup</h1>
		
		<g:form action="save" class="form-horizontal">
			<fieldset>
				<f:all bean="configuration" />
			</fieldset>
			<div class="form-actions">
				<g:submitButton type="submit" class="btn btn-primary" name="Save changes" />
			</div>
		</g:form>
	</body>
</html>