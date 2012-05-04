(function($) {

	var updateStatus = function() {
		// mark all rows with a flag, so we can remove un-updated rows at the end
		$('tr.job').addClass('not-updated');
		
		$.getJSON('status', function(data) {
			_.each(data.jobs, function(job) {
				// find the job row
				var tr = $('#' + job.id);
				if (tr.length == 0) {
					// job not found, reload the page
					location.reload();
				}
				
				// mark the row as updated
				tr.removeClass('not-updated');
				
				// get the status column
				var td = $('td.status', tr);
				
				// hide the div of all the different status
				$('div.status', td).hide();
				
				// show the correct div
				$('div.' + job.status, td).show();
				
				if (job.status == 'RUNNING') {
					// job running, update the title, log and progress bar 
					var percent = (job.progress * 100) + '%';
					var title = job.title + ' (' + percent ')';
					var log = '';
					if (job.log !== undefined) {
						log = jog.log;
					}
					$('.bar', td).css('width', percent);
					$('.title', td).html(title).attr('title', title).data('content', log).popover({
						placement: 'bottom'
					});
					
				} else if (job.status == 'ERROR') {
					// job error, update the error popover
					$('.ERROR span', td).data('content', job.error).popover({
						placement: 'bottom'
					});
				}
			});
			
			// remove un-updated rows
			$("tr.not-updated").fadeOut(1000, function() {
				$(this).empty();
			});
		});
		
		setTimeout(updateStatus, 3000);
	}
	
	updateStatus();
		
})(jQuery);