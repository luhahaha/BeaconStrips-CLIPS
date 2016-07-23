var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var PathResultType = new GraphQLObjectType({
   name: 'PathResult',
   description: '@TODO DESCRIBE ME',

   fields: function getPathResultFields() {
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

         userId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         user: {
            type: getType('User'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('User')
         },

         totalScore: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         startDate: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         endDate: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(PathResultType);
module.exports = PathResultType;