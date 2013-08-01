var util = require('util'),
    thrift = require('thrift'),
    config = require('../config');
	
var ItemRecommendation = require('./gen-nodejs/ItemBasedRecommendation');

var exProtocol = require('./ex_protocol');
	
exports.listItemBasedRecommendations = function(start, limit, callback) {
	var connection = thrift.createConnection(config.thrift.host, config.thrift.port);
	var client = createClient(connection);
	client.listItemBasedRecommendations(0, 15, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

exports.findItemBasedRecommendations = function(userCode, callback) {
	var connection = thrift.createConnection('localhost', 9090);
	var client = createClient(connection);
	client.findItemBasedRecommendations(userCode, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

function createClient(connection) {
	var composite = new exProtocol.TComposite();
	var client = composite.createClient("item_recommendation", ItemRecommendation, connection);

	connection.on('error', function(err) {
	  console.error(err);
	});
	
	return client;
}