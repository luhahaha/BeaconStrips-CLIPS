var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var ProofResultType = new GraphQLObjectType({
   name: 'ProofResult',
   description: '@TODO DESCRIBE ME',

   fields: function getProofResultFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         pathResultId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         pathResult: {
            type: getType('PathResult'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('PathResult')
         },

         startTime: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         endTime: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         score: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(ProofResultType);
module.exports = ProofResultType;