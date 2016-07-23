'use strict';

var SQLRequestHandler = require('./SQLRequestHandler.js');

function BuildingsHandler() {
   this.queryFromRequest = function() {
      return {
         select : '*',
         from   : 'Building'
      };
      //@TODO implementare la chiamata per verificare latitudine e longitudine
   };
}

BuildingsHandler.prototype = new SQLRequestHandler;

module.exports = BuildingsHandler;
