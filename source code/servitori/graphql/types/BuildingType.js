var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var GraphQLFloat = GraphQL.GraphQLFloat;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var BuildingType = new GraphQLObjectType({
   name: 'Building',
   description: '@TODO DESCRIBE ME',

   fields: function getBuildingFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         name: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         description: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         otherInfo: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         openingTime: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         address: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         latitude: {
            type: new GraphQLNonNull(GraphQLFloat),
            description: '@TODO DESCRIBE ME'
         },

         longitude: {
            type: new GraphQLNonNull(GraphQLFloat),
            description: '@TODO DESCRIBE ME'
         },

         telephone: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         email: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         whatapp: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         telegram: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         websiteUrl: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         twitter: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         facebook: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(BuildingType);
module.exports = BuildingType;