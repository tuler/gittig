(function($) {

	var updateStatus = function() {
		$.getJSON('status', function(data) {
			console.log(data.jobs);
		});
	}
	
	var interval = 3000;
	setInterval(updateStatus, interval);
		
})(jQuery);