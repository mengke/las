var util = require('util'),
    thrift = require('thrift'),
    config = require('../config');
	
var HouseVisit = require('./gen-nodejs/HouseVisit');

var exProtocol = require('./ex_protocol');
	
exports.getUserVisitRecords = function(start, limit, callback) {
	var connection = thrift.createConnection(config.thrift.host, config.thrift.port);
	var client = createClient(connection);
	client.getUserVisitRecords(0, 15, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

exports.getUserVisitRecord = function(userCode, houseType, callback) {
	var connection = thrift.createConnection('localhost', 9090);
	var client = createClient(connection);
	client.getUserVisitRecord(userCode, houseType, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

function createClient(connection) {
	var composite = new exProtocol.TComposite();
	var client = composite.createClient("house_visit", HouseVisit, connection);

	connection.on('error', function(err) {
	  console.error(err);
	});
	
	return client;
}