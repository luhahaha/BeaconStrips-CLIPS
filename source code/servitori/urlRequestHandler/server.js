'use strict';

var express = require('express');
var bodyParser = require('body-parser');
var app = express();

const AppInfo = require('./AppInfoHandler.js');
const Beacons = require('./BeaconRequestHandler.js');

// indica di fare il parse del body di tutte le richieste
// in entrata come JSON object
app.use(bodyParser.json());

app.get('/appinfo', function(req, res) {
   console.log('handle appinfo');
   var handler = new AppInfo;
   handler.request  = req;
   handler.response = res;
   handler.execute();
})

app.get('/beacons', function(req, res) {
   console.log('handle beacons');
   var handler = new Beacons;
   handler.request  = req;
   handler.response = res;
   handler.execute();
})

app.listen(1234);

console.log('listening on port 1234');
