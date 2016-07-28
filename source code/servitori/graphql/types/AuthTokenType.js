var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLString = GraphQL.GraphQLString;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLInt = GraphQL.GraphQLInt;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var AuthTokenType = new GraphQLObjectType({
   name: 'AuthToken',
   description: '@TODO DESCRIBE ME',

   fields: function getAuthTokenFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
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

         expirationDate: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         }
      };
   }
});

registerType(AuthTokenType);
module.exports = AuthTokenType;