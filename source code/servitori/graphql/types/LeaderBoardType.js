var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var LeaderBoardType = new GraphQLObjectType({
   name: 'LeaderBoard',
   description: '@TODO DESCRIBE ME',

   fields: function getLeaderBoardFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         pathId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         path: {
            type: getType('Path'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('Path')
         },

         lastUpdate: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         nPlayers: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         topScore: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(LeaderBoardType);
module.exports = LeaderBoardType;