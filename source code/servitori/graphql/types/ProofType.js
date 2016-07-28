var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var ProofType = new GraphQLObjectType({
   name: 'Proof',
   description: '@TODO DESCRIBE ME',

   fields: function getProofFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         stepId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         step: {
            type: getType('Step'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('Step')
         },

         title: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         instructions: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         scoringAlgorithmData: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         testType: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         testData: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         testTitle: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         testInstructions: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(ProofType);
module.exports = ProofType;