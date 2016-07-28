'use strict';

var db = require('./db.js');
var SQLRequestHandler = require('./SQLRequestHandler.js');

function distance(client, building) {
   var R = 6371e3; // metres
   var φ1 = Math.PI * client.latitude / 180;
   var φ2 = Math.PI * building.latitude / 180;
   var Δφ = Math.PI * (building.latitude-client.latitude) / 180;
   var Δλ = Math.PI * (client.longitude-building.longitude) / 180;

   var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ/2) * Math.sin(Δλ/2);
   var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

   var d = R * c;

   return d;
}

function BuildingsHandler() {
   // this.queryFromRequest = function() {
   //    return {
   //       select : '*',
   //       from   : 'Building'
   //    };
   //    //@TODO implementare la chiamata per verificare latitudine e longitudine
   // };

   this.execute = function() {

      var data = this.request.body;

      if (!data.hasOwnProperty('latitude')) {
         this.response.status(461).send({
            errorCode: 461,
            debugError: 'missing field: latitude',
         });
      }
      if (!data.hasOwnProperty('longitude')) {
         this.response.status(461).send({
            errorCode: 461,
            debugError: 'missing field: longitude',
         });
      }
      if (!data.hasOwnProperty('maxDistance')) {
         this.response.status(461).send({
            errorCode: 461,
            debugError: 'missing field: maxDistance',
         });
      }

      var latitude = data.latitude;
      var longitude = data.longitude;
      var maxDistance = data.maxDistance;

      var client = {
         latitude: latitude,
         longitude: longitude
      };

      var distanceFilter = function(building) {
         return distance(client, building) < maxDistance;
      };

      var context = {
         filter: distanceFilter,
         response: this.response,
         addPathToBuildings: this.addPathToBuildings
      };

      var query = db().from('Building');
      query.then(function(result) {
         console.log('got buildings: ', result);
         var selected = result.filter(context.filter);
         console.log('selected: ', selected);
         var fillWithPaths = context.addPathToBuildings(selected);
         fillWithPaths.then(function(buildings) {
            console.log('got buildings with paths: ', buildings);
            context.response.status(200).send(buildings);
         }.bind(context), function(error) {
            console.error('got error fulfilling buildings: ', error);
            context.response.status(505).send({
               errorCode: 505,
               debugMessage: 'error fulfilling buildings',
               debugInfo: error
            });
         }.bind(context));
      }.bind(context), function(error) {
         console.error('error getting buildings ', error);
         context.response.status(505).send({
            errorCode: 505,
            debugMessage: 'error getting buildings',
            debugInfo: error
         });
      }.bind(context));
   };

   this.addPathToBuildings = function (buildings) {
      var promises = [];
      console.log('this: ', this);
      for (let building of buildings) {
         // promises.push(this.buildingWithPaths(building));

         var promise = new Promise(function(resolve, reject) {
            var query = db().from('Path').where({buildingID : building.id});
            query.then(function(result) {
               building.paths = result;
               resolve(building);
            }.bind(building), function(error) {
               console.error('error getting paths for building: ', building, '\nERROR: ', error);
               reject(building);
            });
         });
         promises.push(promise);
      }
      return Promise.all(promises);
   };

   this.buildingWithPaths = function(building) {
      var promise = new Promise(function(resolve, reject) {
         var query = db().from('Path').where({buildingID : building.id});
         query.then(function(result) {
            building.paths = result;
            resolve(building);
         }.bind(building), function(error) {
            console.error('error getting paths for building: ', building, '\nERROR: ', error);
            reject(building);
         });
      });
      return promise;
   };
};

BuildingsHandler.prototype = new SQLRequestHandler;

module.exports = BuildingsHandler;
