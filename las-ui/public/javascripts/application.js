$( document ).ready(function() {
	var socket = io.connect();
	
	$('#load_users').click(function() {
		socket.emit('list_users_visits', function (data) {
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
		socket.emit('find_visits', $('#user_code').val(), house_type, function (data) {
			$('#visit_history').html('');
			$('#visit_history').html(data);
		});
	});
	$('#search_item_recommendations').click(function() {
		socket.emit('item_recommendations', $('#user_code_item_rec').val(), function (data) {
			$('#item_recommendations_list').html('');
			$('#item_recommendations_list').html(data);
		});
	});
});