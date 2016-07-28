var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var ProximityType = new GraphQLObjectType({
   name: 'Proximity',
   description: '@TODO DESCRIBE ME',

   fields: function getProximityFields() {
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

         percentage: {
            type: GraphQLInt,
            description: 'da -100 a +100'
         },

         textToDisplay: {
            type: GraphQLInt,
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(ProximityType);
module.exports = ProximityType;