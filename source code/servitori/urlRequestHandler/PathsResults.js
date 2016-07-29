 'use strict';

var userID = require('./UserIDRetriever.js');
var db = require('./db.js');
var SQLRequestHandler = require('./SQLRequestHandler.js');

function PathsResultsHandler() {
   this.execute = function() {
      var token = this.token();
      if (token) {
         var id = userID(token);
         id.then(function(id) {
            if (id) {
               var pathsQuery = db().select().from('PathResult').where({
                  userID: id
               });
               pathsQuery.then(function(pathsResults) {
                  var promises = [];
                  for (var path of pathsResults) {
                     var promise = new Promise(function(resolve, reject) {
                        var proofsQuery = db().select().from('ProofResult').where({
                           pathResultID: path.id
                        });
                        proofsQuery.then(function(proofResults) {
                           console.log('proofResults: ', proofResults);
                           path.proofResults = proofResults;
                           resolve(path);
                        }.bind(path), reject.bind(this));
                     });
                     promises.push(promise);
                  }
                  console.log('did create promises: ', promises);
                  var all = Promise.all(promises);
                  var response = this.response;
                  all.then(function(pathsResults) {
                     console.log('did end all promises');
                     response.status(200).send(pathsResults);
                  }.bind(response), function(error) {
                     console.error('error getting proof results: ', error);
                     response.status(505).send({
                        errorCode: 505,
                        debugMessage: 'Error getting proof results',
                        errorInfo: error
                     });
                  }.bind(response))
               }.bind(this), function(error) {
                  console.error('error getting paths results: ', error);
                  this.response.status(505).send({
                     errorCode: 505,
                     debugMessage: 'Error getting paths results',
                     errorInfo: error
                  });
               }.bind(this));
            } else {
               this.response.status(401).send({
                  message: 'il token ' + token + ' non esiste'
               });
            }
         }.bind(this), function(error) {
            console.log('error finding userId = ', error);
            this.response.status(500).send();
         }.bind(this));
      } else {
         console.log('no token');
         this.response.status(405).send({
            errorCode: 405,
            debugMessage: "non è stato fornito alcun token"
         });
      }
   }
}

PathsResultsHandler.prototype = new SQLRequestHandler;

module.exports = PathsResultsHandler;
