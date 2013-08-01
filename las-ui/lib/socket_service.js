var util = require('util'),
		jade = require('jade');
    thrift = require('thrift');
	
var socketio = require('socket.io');
var io;

var las_services = require('./las-services');


exports.listen = function(server) {
	io = socketio.listen(server);
	io.set('log level', 1);
	
	io.sockets.on('connection', function (socket) {
	  socket.on('list_users_visits', function (callback) {
		  las_services.listUserVisitRecords(0, 15, function(err, response) {
		  	if (err) {
		  		console.error(err);
		  	} else {
		  		var context = {"users" : response};
		  		var houses_history_users = jade.renderFile('./views/house_visits_users.jade', context, function (error, html) {
		  			if (error) {
		  				console.error(error);
		  			} else {
		  				callback(html);
		  			}
		  		});
		  		
		  	}
		  });
	  });
	  
	  socket.on('find_visits', function (userCode, houseType, callback) {
		  las_services.findVisitRecords(userCode, houseType, function(err, response) {
		  	if (err) {
		  		console.error(err);
		  	} else {
		  		var context = {"houses" : response};
					var houses_history_search = jade.renderFile('./views/house_visits_search.jade', context, function (error, html) {
						if (error) {
		  				console.error(error);
		  			} else {
		  				callback(html);
		  			}
		  		});
		  	}
		  });
	    
	  });
	  
	  socket.on('item_recommendations', function (userCode, callback) {
		  las_services.findItemBasedRecommendations(userCode, function(err, response) {
		  	if (err) {
		  		console.error(err);
		  	} else {
		  		var context = {"items" : response};
					var houses_history_search = jade.renderFile('./views/item_recommendations.jade', context, function (error, html) {
						if (error) {
		  				console.error(error);
		  			} else {
		  				callback(html);
		  			}
		  		});
		  	}
		  });
	    
	  });
	});
	
};
