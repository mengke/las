
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , services = require('./routes/services')
  , http = require('http')
  , path = require('path')
  , config = require('./config');
  
var socket_service = require('./lib/socket_service');

var app = express();

// all environments
app.configure(function() {
	app.set('port', config.express.port);
	app.set('views', __dirname + '/views');
	app.set('view engine', config.express.view_engine);
	app.use(express.favicon());
	app.use(express.logger('dev'));
	app.use(express.bodyParser());
	app.use(express.methodOverride());
	app.use(require('stylus').middleware(__dirname + '/public'));
	app.use(express.static(path.join(__dirname, 'public')));
	app.use(app.router);
	app.use(function(err, req, res, next){
	  if (err instanceof NotFound) {
        res.render('404', {  
		        status: 404,  
		        title: 'Page Not Found'
		    }); 
    } else {
    		console.error(err.stack);
    }
	});
});

// development only
app.configure('development', function() {
	app.use(express.errorHandler());
});

app.get('/', routes.index);
app.get('/services', services.list);

app.get('*', function(req, res) {  
    console.error('404 not found: ' + req.url);  
    throw new NotFound;
});

function NotFound(msg){
    this.name = 'NotFound';
    Error.call(this, msg);
    Error.captureStackTrace(this, arguments.callee);
}

var server = http.createServer(app);

socket_service.listen(server);
server.listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

