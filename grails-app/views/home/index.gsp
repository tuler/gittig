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
					<h5><g:createLink controller="hook" action="${hook}" absolute="true" /></h5>
				</div>
			</li>
			</g:each>
		</ul>
		<sec:ifLoggedIn>
			<table class="table table-striped">
				<thead>
					<tr>
						<th><g:message code="hookJob.repository.label" /></th>
						<th></th>
						<th><g:message code="hookJob.origin.label" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<g:if test="${repos}">
					<g:each in="${repos}" var="repo">
						<tr>
							<td>${repo.path}</td>
							<td><g:link controller="queue" action="enqueue" class="btn btn-small" params="[path: repo.path, remote: repo.remote]"><i class="icon-chevron-left"></i>Update</g:link></td>
							<td><code>${repo.remote}</code></td>
						</tr>
					</g:each>
					</g:if>
					<g:else>
						<tr>
							<td colspan="4"><g:message code="hookJob.no_repositories.label" /></td>
						</tr>
					</g:else>
				</tbody>
			</table>
		</sec:ifLoggedIn>
	</body>
</html>