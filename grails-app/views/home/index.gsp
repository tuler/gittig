<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
   </head>
	<body>
		<ul class="thumbnails">
			<g:each in="${hooks}" var="hook">
			<li class="span4">
				<div class="thumbnail">
					<img src="${resource(dir: 'images', file: hook + '.png')}" width="150" />
					<center><h5><g:createLink controller="hook" action="${hook}" absolute="true" /></h5></center>
				</div>
			</li>
			</g:each>
		</ul>
		<sec:ifLoggedIn>
			<table class="table table-striped">
				<thead>
					<tr>
						<th><g:message code="repo.path.label" /></th>
						<th><sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_ADMINISTRATORS"><g:link controller="queue" action="enqueueAll" class="btn btn-small"><i class="icon-chevron-left"></i><g:message code="repo.updateAll.label" /></g:link></sec:ifAnyGranted></th>
						<th><g:message code="repo.url.label" /></th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${repos}">
					<g:each in="${repos}" var="repo">
						<tr>
							<td>${repo.path}</td>
							<td><sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_ADMINISTRATORS"><g:if test="${repo.remote?.length() > 0}"><g:link controller="queue" action="enqueue" class="btn btn-small" params="[remote: repo.remote]"><i class="icon-chevron-left"></i><g:message code="repo.update.label" /></g:link></g:if></sec:ifAnyGranted></td>
							<td><code>${repo.remote}</code></td>
						</tr>
					</g:each>
					</g:if>
					<g:else>
						<tr>
							<td colspan="3"><g:message code="hookJob.no_repositories.label" /></td>
						</tr>
					</g:else>
				</tbody>
			</table>
		</sec:ifLoggedIn>
	</body>
</html>