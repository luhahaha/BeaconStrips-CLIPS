var mysql      = require('db-mysql');

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
