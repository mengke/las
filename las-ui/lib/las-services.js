var util = require('util'),
    thrift = require('thrift'),
    config = require('../config');
	
var LasService = require('./gen-nodejs/LasService');
	
exports.listUserVisitRecords = function(start, limit, callback) {
	var connection = thrift.createConnection(config.thrift.host, config.thrift.port);
	var client = createClient(connection);
	client.listUserVisitRecords(0, 15, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

exports.findVisitRecords = function(userCode, houseType, callback) {
	var connection = thrift.createConnection(config.thrift.host, config.thrift.port);
	var client = createClient(connection);
	client.findVisitRecords(userCode, houseType, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

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
	var connection = thrift.createConnection(config.thrift.host, config.thrift.port);
	var client = createClient(connection);
	client.findItemBasedRecommendations(userCode, function(err, response) {
		callback(err, response);
		setTimeout(function() {
			connection.end();
		}, 100);
	});
}

function createClient(connection) {
  var client = thrift.createClient(LasService, connection);

	connection.on('error', function(err) {
	  console.error(err);
	});
	
	return client;
}