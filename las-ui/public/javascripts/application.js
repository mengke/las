$( document ).ready(function() {
	var socket = io.connect();
	
	$('#load_users').click(function() {
		socket.emit('users', function (data) {
			$('#show_info').removeClass('hide');
			$('#housevisits_list').html("");
			$('#housevisits_list').html(data);
			$('#user_0').addClass('in');
		});
	});
	
	$('#search_history').click(function() {
		var house_type_id = $('.house_type.active').attr('id');
		var house_type = 0;
		if ('all_house_type' == house_type_id) {
			house_type = 0;
		} else if ('sell_house_type' == house_type_id) {
			house_type = 1;
		} else {
			house_type = 2;
		}
		socket.emit('visit_history', $('#user_code').val(), house_type, function (data) {
			$('#visit_history').html('');
			console.log(data);
			$('#visit_history').html(data);
		});
	});
});