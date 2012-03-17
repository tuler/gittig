<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
   </head>
	<body>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Repository</th>
					<th>Origin</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${jobs}">
				<g:each in="${jobs}" var="job">
					<tr>
						<td>${job.path}</td>
						<td><code>${job.url}</code></td>
						<!--td width="200px">
							<div class="progress progress-info progress-striped progress-animated active">
								<div id="progress" class="bar" style="width: 40%"></div>
							</div>
						</td-->
					</tr>
				</g:each>
				</g:if>
				<g:else>
					<tr>
						<td colspan="3">No jobs</td>
					</tr>
				</g:else>
			</tbody>
		</table>
	</body>
</html>