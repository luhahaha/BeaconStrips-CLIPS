'use strict';

var bodyParser = require('body-parser');
var express    = require('express');
var app        = express();
require('./mysqlSetup.js');

/*
spostata nel file mysqlSetup.js

l'idea è che la configurazione sia fatta in quel file, in modo che
ciascuno possa tenerne una copia in locale con i dati del proprio db
new mysql.Database({
   hostname: 'localhost',
   user: 'root',
   password: '',
   database: 'persone'
}).connect(function (error) {
   if (error) {
      console.log("connection error: " + error);
   } else {
      console.log("did connect");
   }
}
*/

// indica di fare il parse del body di tutte le richieste
// in entrata come JSON object
app.use(bodyParser.json());

/// super classe che rappresenta l'handler di una richiesta URL
var RequestHandler = function( request, response ) {
   this.request = request;
   this.response = response;

   /// funzione che ottiene i dati cercati e li invia
   /// in risposta
   this.execute = function() {
      console.log('execute MUST be overridden \
      by all RequestHandler subclasses to create \
      the respose object and send it');
      console.error('execute MUST be overridden \
      by all RequestHandler subclasses to create \
      the respose object and send it');
   }
}

/// classe che rappresenta la richiesta delle info sull'app
var AppInfoHandler = function( request, response ) {
   this.__proto__ = new RequestHandler(request, response);
   this.execute   = function() {
      // rispondo con lo stato 200 e le info sull'app
      response.status(200).send({
         description  : 'Descrizione dell\'applicazione',
         websiteURL   : '52.58.6.246',
         supportemail : 'beaconstrips.swe@gmail.com',
         discoveryUUID: 'asdfhjlk-hjkl-fdas-jklh-fdas-fjdkalhd'
      })
   }
}

/// classe che rappresenta l'handler di una richiesta che richiede l'accesso al db
var SQLRequestHandler = function( request, response ) {
   this.__proto__ = new RequestHandler(request, response);

   /// query da eseguire nel DB per ritornare i dati selezionati
   this.queryFromRequest = function () {
      console.log('queryFromRequest MUST be overridden \
      by all SQLRequestHandler subclasses to provide the \
      query for the db');
   }

   this.execute = function() {
      // prendo la query
      var query = this.queryFromRequest();
      mysql.query(query).execute( function (error, result ) {
         if (error) {
            console.error("error : " + error);
            this.response.status(300).send(result);
         } else {
            this.response.status(200).send(result);
         }
      });
   }
}

/// implementazione esempio (funziona solo nel db locale di tom139)
var PersoneHandler = function (request, response) {
   this.__proto__ = new SQLRequestHandler(request, response);

   this.queryFromRequest = function (req) {
      return "SELECT * FROM Persone"
   }
}

app.get('/appinfo', function (req, res) {
   AppInfoHandler(req, res).execute();
})

app.get('/persone', function (req, res) {
   PersoneHandler(req, res).execute();
})

app.listen(1234);


//
//
// var URLRequestHandler = function(request, response) {
// 	this.request = request;
// 	this.response = response;
// 	this.responseJson = {};
//
// 	this.setupResponse = function() {
// 		console.log('This function must be overriden by subclasses and\
// 		must fullfill the response json object');
// 		return false;
// 	}
// 	this.sendResponse = function() {
// 		response.send(this.responseJson)
// 	}
// 	this.sendError = function(status, error) {
// 		response.status(status).send(error)
// 	}
// 	this.execute = function() {
// 		console.log("1");
// 		var didSetupResponse = this.setupResponse();
// 		console.log("2");
// 		if (didSetupResponse) {
// 			console.log(3);
// 			this.sendResponse();
// 		} else {
// 			console.log(4);
// 			this.sendError(500, {errore : "errore"});
// 		}
// 		console.log(5);
// 	}
// }
// console.log('banana');
//
// var AppInfoProvider = new URLRequestHandler()
// console.log('banana');
//
// var AppInfoProvider = function(request, response) {
// 	this.__proto__ = new URLRequestHandler(request, response);
//
// 	this.setupResponse = function() {
// 		this.responseJson = {
// 			description : "descrizione un due tre",
// 			websiteURL  : "52.58.6.246",
// 			discoveryUUID: "asdfhjlk-hjkl-fdas-jklh-fdas-fjdkalh"
// 		};
// 		return true;
// 	}
// 	console.log('banana');
// }
// console.log('banana');
//
// app.get('/', function(req, res) {
// 	var resp = new AppInfoProvider(req, res)
// 	resp.execute();
// })
// console.log('banana');
//
// app.listen(1234);
// console.log('banana');
