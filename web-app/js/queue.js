(function($) {

	var updateStatus = function() {
		$.getJSON('status', function(data) {
			_.each(data.jobs, function(job) {
				var tr = $('#' + job.id);
				var td = $('td.status', tr);
				$('div.status', td).hide();
				$('div.' + job.status, td).show();
				
				if (job.status == 'RUNNING') {
					$('.title', td).html(job.title);
					job.progress = 0.5;
					$('.bar', td).css('width', (job.progress * 100) + '%');
				} else if (job.status == 'ERROR') {
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