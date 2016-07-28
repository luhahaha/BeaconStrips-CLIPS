'use strict';
var getEntityResolver = require('./util/entity-resolver');
var GraphQL = require('graphql');
var AuthTokenType = require('./types/AuthTokenType');
var BeaconType = require('./types/BeaconType');
var BuildingType = require('./types/BuildingType');
var LeaderBoardType = require('./types/LeaderBoardType');
var PathType = require('./types/PathType');
var PathResultType = require('./types/PathResultType');
var ProofType = require('./types/ProofType');
var ProofResultType = require('./types/ProofResultType');
var ProximityType = require('./types/ProximityType');
var ScoreType = require('./types/ScoreType');
var StepType = require('./types/StepType');
var UserType = require('./types/UserType');
var resolveMap = require('./resolve-map');
var types = require('./types');
var GraphQLObjectType = GraphQL.GraphQLObjectType;
var GraphQLSchema = GraphQL.GraphQLSchema;
var GraphQLNonNull = GraphQL.GraphQLNonNull;
var GraphQLString = GraphQL.GraphQLString;
var GraphQLInt = GraphQL.GraphQLInt;
var registerType = resolveMap.registerType;

var schema = new GraphQLSchema({
   query: new GraphQLObjectType({
      name: 'RootQueryType',

      fields: function getRootQueryFields() {
         return {
            authToken: {
               type: AuthTokenType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLString)
                  }
               },

               resolve: getEntityResolver('AuthToken')
            },

            beacon: {
               type: BeaconType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Beacon')
            },

            building: {
               type: BuildingType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Building')
            },

            leaderBoard: {
               type: LeaderBoardType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('LeaderBoard')
            },

            path: {
               type: PathType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Path')
            },

            pathResult: {
               type: PathResultType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('PathResult')
            },

            proof: {
               type: ProofType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Proof')
            },

            proofResult: {
               type: ProofResultType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('ProofResult')
            },

            proximity: {
               type: ProximityType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Proximity')
            },

            score: {
               type: ScoreType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Score')
            },

            step: {
               type: StepType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('Step')
            },

            user: {
               type: UserType,

               args: {
                  id: {
                     name: 'id',
                     type: new GraphQLNonNull(GraphQLInt)
                  }
               },

               resolve: getEntityResolver('User')
            }
         };
      }
   })
});

module.exports = schema;