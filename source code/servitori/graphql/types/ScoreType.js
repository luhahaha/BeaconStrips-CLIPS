var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var ScoreType = new GraphQLObjectType({
   name: 'Score',
   description: '@TODO DESCRIBE ME',

   fields: function getScoreFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         leaderBoardId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         leaderBoard: {
            type: getType('LeaderBoard'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('LeaderBoard')
         },

         userId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         user: {
            type: getType('User'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('User')
         },

         score: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(ScoreType);
module.exports = ScoreType;