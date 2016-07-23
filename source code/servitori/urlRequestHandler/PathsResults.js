'use strict';

var userID = require('./UserIDRetriever.js');
var db = require('./db.js');
var SQLRequestHandler = require('./SQLRequestHandler.js');

function PathsResultsHandler() {
   this.execute = function() {
      var token = this.token();
      if (token) {
         var id = userID(token);
         var handler = this;
         id.then(function(id) {
            if (id) {
               handler.response.status(200).send({
                  message: 'implementare chiamate per tornare risultati dell\'utente con id: ' + id
               });
            } else {
               handler.response.status(401).send({
                  message: 'il token ' + token + ' non esiste'
               });
            }
         }, function(error) {
            console.log('error finding userId = ', error);
            handler.response.status(500).send();
         });
      } else {
         console.log('no token');
      }
   }
}

PathsResultsHandler.prototype = new SQLRequestHandler;

module.exports = PathsResultsHandler;
