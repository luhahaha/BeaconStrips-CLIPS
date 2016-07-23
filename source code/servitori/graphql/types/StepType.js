var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var StepType = new GraphQLObjectType({
   name: 'Step',
   description: '@TODO DESCRIBE ME',

   fields: function getStepFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         stopBeaconId: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         stopBeacon: {
            type: getType('Beacon'),
            description: '@TODO DESCRIBE ME (reference)',
            resolve: getEntityResolver('Beacon')
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

         position: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(StepType);
module.exports = StepType;