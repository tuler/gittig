<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
		<r:require modules="queue" />
   </head>
	<body>
		<table class="table table-striped">
			<thead>
				<tr>
					<th><g:message code="hookJob.dateCreated.label" /></th>
					<th><g:message code="hookJob.repository.label" /></th>
					<th><g:message code="hookJob.origin.label" /></th>
					<th><g:message code="hookJob.status.label" /></th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${jobs}">
					<g:each in="${jobs}" var="job">
						<tr class="job" id="${job.id}">
							<td><g:formatDate date="${job.dateCreated}" type="datetime" /></td>
							<td>${job.path}</td>
							<td><code>${job.url}</code></td>
							<td width="200px" class="status">
								<div class="WAITING status" style="display: none;">
									<span class="label"><g:message code="hookJob.status.WAITING" /></span>
								</div>
								<div class="ERROR status" style="display: none;">
									<span class="label label-important" data-content="" title="${message(code: 'hookJob.status.ERROR')}"><g:message code="hookJob.status.ERROR" /></span>
								</div>
								<div class="RUNNING status" style="display: none;">
									<p class="title"></p>
									<div class="progress progress-striped active"><div class="bar" data-content="" title="" style="width: 0%;"></div></div>
								</div>
								<div class="DISCARDED status" style="display: none;">
									<span class="label"><g:message code="hookJob.status.DISCARDED" /></span>
								</div>
								<div class="CANCELLED status" style="display: none;">
									<span class="label"><g:message code="hookJob.status.CANCELLED" /></span>
								</div>
								<div class="COMPLETED status" style="display: none;">
									<span class="label label-success"><g:message code="hookJob.status.COMPLETED" /></span>
								</div>
							</td>
						</tr>
					</g:each>
				</g:if>
				<g:else>
					<tr>
						<td colspan="4"><g:message code="hookJob.no_jobs.label" /></td>
					</tr>
				</g:else>
			</tbody>
		</table>
	</body>
</html>