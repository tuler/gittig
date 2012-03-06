<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
   </head>
	<body>
		<h1>Setup</h1>
		
		<g:form action="save" class="form-horizontal">
			<fieldset>
				<div class="control-group">
					<label class="control-label">Git Version</label>
					<div class="controls">
						<g:if test="${configuration?.gitVersion}">
							<span class="label label-success">${configuration.gitVersion}</span>
						</g:if>
						<g:else>
							<span class="label label-important">git not installed</span>
						</g:else>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Repository Location</label>
					<div class="controls">
						<g:textField name="baseDir" value="${configuration?.baseDir}" />
						<g:select name="locationResolver" from="${['nameLocationResolver', 'usernameLocationResolver', 'serviceLocationResolver']}" valueMessagePrefix="locationResolver" value="${configuration?.locationResolver}"/>
						<p class="help-block">i.e. git@github.com:tuler/git-mirror.git</p>
					</div>
				</div>
			</fieldset>
			<div class="form-actions">
				<g:submitButton type="submit" class="btn btn-primary" name="Save changes" />
			</div>
		</g:form>
	</body>
</html>