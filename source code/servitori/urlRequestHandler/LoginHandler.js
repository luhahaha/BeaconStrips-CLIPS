'use strict';

var db = require('./db.js');
var URLRequestHandler = require('./URLRequestHandler.js');

function BeaconRequest() {
   this.userID = function(email, password) {
      var promise = new Promise(function(resolve, reject){
         var query = db().select('id').from('User').where({
            email: email,
            password: password
         });
         query.then(function(result){
            resolve(result[0]['id']);
         }, function(error) {
            console.log('failure with error ', error);
            reject(error);
         });
      });
      return promise;
   };

   /*

   this.checkIfTokenIsUnique = function(uuid) {
      var promise = new Promise(function(resolve, reject) {
         var query = db().count('id').from('AuthToken').where({
            token: uuid
         });
         query.then(function(result){
            console.log('checkIfTokenExists result = ', result);
            resolve(false);
         }, function(error){
            reject(error);
         })
      };
   };

   */

   this.generateToken = function() {
      var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
         var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
         return v.toString(16);
      });
      return uuid;
   };

   /*

   this.getUniqueToken = function() {
      console.log('getUniqueToken');
      var promise = new Promise(function(resolve, reject){
         var uuid = this.generateToken();
         var query = db().count('id').from('AuthToken').where({
            token: uuid
         });
         query.then(function(result) {
            console.log('token exists = ', result);
            if (result) {
               resolve(uuid);
            } else {
               this.getUniqueToken.then(resolve, reject);
            }
         }, function(error) {
            console.log('error checking if token is unique: ', error);
         });
      });
      return promise;
   };

   */

   /*

   this.tokenIsUnique = function(token, isUnique) {
      var promise = new Promise(function(resolve, reject) {
         if (isUnique) {
            resolve(token);
         } else {
            var uuid = this.generateToken();
            this.checkIfTokenIsUnique(uuid).then(function(isUnique){

            }, function(error) {
               reject(error);
            }))
         }
      });
      return promise;
   }

   this.createToken = function() {
      var promise = new Promise(function(resolve, reject) {
         var uuid = this.generateToken;
         this.checkIfTokenExists(uuid).then(function(exists){
            if (exists) {
               resolve(uuid);
            } else {
               var newUUID = this.generateToken;
               checkIfTokenExists(newUUID
            }
         }, function(error) {

         });
      })
   };

   */

   this.execute = function() {
      var email = this.request.body.email;
      var password = this.request.body.password;
      console.log('email = ', email, '\npassword = ', password);
      var handler = this;
      this.userID(email, password).then(function(userID) {
         if (userID) {
            console.log('esiste e l\'id è', userID);
            // console.log('this = ', handler);
            var token = handler.generateToken();
            console.log('il token è ', token);
            handler.response.status(200).send({
               token: token
            });
            var query = db()('AuthToken').insert({
               token: token,
               userID: userID,
               expirationDate: '2016-12-31'
            });
            console.log('query = ', query.toSQL());
            query.then(function(result) {
               console.log('result = ', result);
            }, function(error) {
               console.log('error = ', error);
            })
         } else {
            console.log('non esiste!');
            handler.response.status(401).send({
               message: "l'utente non esiste"
            });
         }
      }, function(error) {
         console.log('error = ', error);
         console.error('error = ', error);
      });
   };
}

BeaconRequest.prototype = new URLRequestHandler;

module.exports = BeaconRequest;
