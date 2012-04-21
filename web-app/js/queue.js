(function($) {

	var updateStatus = function() {
		$.getJSON('status', function(data) {
			console.log(data);
		});
	}
	
	var interval = 3000;
	setInterval(updateStatus, interval);
		
})(jQuery);