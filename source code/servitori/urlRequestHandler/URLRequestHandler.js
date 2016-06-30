'use strict';

var bodyParser = require('body-parser');
var express = require('express');
var app = express();

app.use(bodyParser.json());
console.log('banana');

var URLRequestHandler = function(request, response) {
	this.request = request;
	this.response = response;
	this.responseJson = {};

	this.setupResponse = function() {
		console.log('This function must be overriden by subclasses and\
		must fullfill the response json object');
		return false;
	}
	this.sendResponse = function() {
		response.send(this.responseJson)
	}
	this.sendError = function(status, error) {
		response.status(status).send(error)
	}
	this.execute = function() {
		console.log("1");
		var didSetupResponse = this.setupResponse();
		console.log("2");
		if (didSetupResponse) {
			console.log(3);
			this.sendResponse();
		} else {
			console.log(4);
			this.sendError(500, {errore : "errore"});
		}
		console.log(5);
	}
}
console.log('banana');

var AppInfoProvider = new URLRequestHandler()
console.log('banana');

var AppInfoProvider = function(request, response) {
	this.__proto__ = new URLRequestHandler(request, response);

	this.setupResponse = function() {
		this.responseJson = {
			description : "descrizione un due tre",
			websiteURL  : "52.58.6.246",
			discoveryUUID: "asdfhjlk-hjkl-fdas-jklh-fdas-fjdkalh"
		};
		return true;
	}
	console.log('banana');
}
console.log('banana');

app.get('/', function(req, res) {
	var resp = new AppInfoProvider(req, res)
	resp.execute();
})
console.log('banana');

app.listen(1234);
console.log('banana');
