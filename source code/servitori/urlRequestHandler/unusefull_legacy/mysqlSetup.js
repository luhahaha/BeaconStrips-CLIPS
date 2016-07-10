'use strict';

var mysql = require('db-mysql');

new mysql.Database({
   hostname: 'localhost',
   user: 'root',
   password: '',
   database: 'CLIPS'
}).connect(function (error) {
   if (error) {
      console.log("connection error: " + error);
   } else {
      console.log("did connect");
   }
});

module.exports = mysql;
