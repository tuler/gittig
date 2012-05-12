(function($) {

	var updateJobStatus = function(job) {
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
		
		// cancel button
		if (job.status == 'RUNNING' || job.status == 'QUEUED') {
			// show the cancel button
			$('td.action a', tr).show();
		} else {
			// hide the cancel button
			$('td.action a', tr).hide();
		}
		
		if (job.status == 'RUNNING') {
			// job running...
			
			// update progress bar
			var percent = Math.floor(job.progress * 100) + '%';
			$('.bar', td).css('width', percent);

			// update the title
			if (job.title) {
				var title = job.title + ' (' + percent + ')';
				$('.title', td).html(title);
				$('.bar', td).attr('title', title);
			}
			
			if (job.result) {
				// replace carriage return with html line break
				var result = job.result.replace(/\r?\n|\r/g, '<br>');
				$('.bar', td).data('content', result).popover({
					placement: 'bottom'
				});
			}

		} else if (job.status == 'ERROR') {
			// job error, update the error popover
			$('.ERROR span', td).data('content', job.error).popover({
				placement: 'bottom'
			});
		}
	}
	
	var updateStatus = function() {
		// mark all rows with a flag, so we can remove un-updated rows at the end
		$('tr.job').addClass('not-updated');
		
		$.getJSON('status', function(data) {
			_.each(data.jobs, function(job) {
				updateJobStatus(job);
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