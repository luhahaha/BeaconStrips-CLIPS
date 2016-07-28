var getEntityResolver = require('../util/entity-resolver');
var resolveMap = require('../resolve-map');
var GraphQL = require('graphql');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLInt = GraphQL.GraphQLInt;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var GraphQLList = GraphQL.GraphQLList;
var getType = resolveMap.getType;
var registerType = resolveMap.registerType;

var PathType = new GraphQLObjectType({
   name: 'Path',
   description: '@TODO DESCRIBE ME',

   fields: function getPathFields() {
      return {
         id: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         title: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         description: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         target: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         startingMessage: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         rewardMessage: {
            type: new GraphQLNonNull(GraphQLString),
            description: '@TODO DESCRIBE ME'
         },

         position: {
            type: new GraphQLNonNull(GraphQLInt),
            description: '@TODO DESCRIBE ME'
         },

         steps: {
            type: new GraphQLList(getType('Step')),
            description: 'Steps belonging to this Path',
            resolve: getEntityResolver('Step'),

            args: {
               limit: {
                  name: 'limit',
                  type: GraphQLInt
               },

               offset: {
                  name: 'offset',
                  type: GraphQLInt
               }
            }
         }
      };
   }
});

registerType(PathType);
module.exports = PathType;