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
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Repository</th>
					<th>Origin</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${repos}">
				<g:each in="${repos}" var="repo">
					<tr>
						<td>${repo.path}</td>
						<td><code>${repo.remote}</code></td>
						<td><a class="btn btn-mini"><i class="icon-download"></i> Update</a></td>
					</tr>
				</g:each>
				</g:if>
				<g:else>
					<tr>
						<td colspan="3">No repositories mirrored yet</td>
					</tr>
				</g:else>
			</tbody>
		</table>
	</body>
</html>