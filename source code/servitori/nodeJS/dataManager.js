var express = require('express')
  , http = require('http')
  , mysql = require('mysql'); // <---- HERE

var app = express();

var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: "",
    database: 'persone'
});

connection.connect(); // <---- AND HERE

// all environments
app.set('port', process.env.PORT || 7002);


app.get('/',function(request,response){
connection.query('SELECT * FROM persone', function(err, rows, fields)

    {
            console.log('Connection result error '+err);
            console.log('no of records is '+rows.length);
                    response.writeHead(200, { 'Content-Type': 'application/json'});
            response.end(JSON.stringify(rows));
    });

} );

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

var URLRequestHandler = function (req, response) {
	console.log('handler della request');
	this.request  = req;
	this.response = response;
}

URLRequestHandler.prototype.getData = function () {

};

URLRequestHandler.prototype.respond = function () {

};

var DBResponseHandler = function (req, response, query) {
	console.log('db request handler');
	this = new URLRequestHandler;
	this.query = query;
}

DBResponseHandler.query =
