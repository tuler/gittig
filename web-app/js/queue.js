(function($) {

	var updateStatus = function() {
		$.getJSON('status', function(data) {
			_.each(data.jobs, function(job) {
				// find the job row
				var tr = $('#' + job.id);
				if (tr.length == 0) {
					// job not found, reload the page
					location.reload();
				}
				
				// get the status column
				var td = $('td.status', tr);
				
				// hide the div of all the different status
				$('div.status', td).hide();
				
				// show the correct div
				$('div.' + job.status, td).show();
				
				if (job.status == 'RUNNING') {
					// job running, update the progress bar
					$('.title', td).html(job.title);
					$('.bar', td).css('width', (job.progress * 100) + '%');
					
				} else if (job.status == 'ERROR') {
					// job error, update the error popover
					$('.ERROR span', td).data('content', job.error).popover({
						placement: 'bottom'
					});
				}
			});
		});
		
		setTimeout(updateStatus, 3000);
	}
	
	updateStatus();
		
})(jQuery);