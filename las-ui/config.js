module.exports = {
	express: {
		port: process.env.PORT || 3000,
		view_engine: 'jade',
	},
	
	thrift: {
		host: 'localhost',
		port: 9090
	}
};