'use strict';

var resolveMap = {
   'AuthToken': {
      'name': 'AuthToken',
      'table': 'AuthToken',
      'primaryKey': 'token',

      'aliases': {
         'token': 'id',
         'userID': 'userId'
      },

      'referenceMap': {
         'user': 'userId'
      },

      'listReferences': {}
   },

   'Beacon': {
      'name': 'Beacon',
      'table': 'Beacon',
      'primaryKey': 'id',

      'aliases': {
         'UUID': 'uuid'
      },

      'referenceMap': {},
      'listReferences': {}
   },

   'Building': {
      'name': 'Building',
      'table': 'Building',
      'primaryKey': 'id',

      'aliases': {
         'websiteURL': 'websiteUrl'
      },

      'referenceMap': {},
      'listReferences': {}
   },

   'LeaderBoard': {
      'name': 'LeaderBoard',
      'table': 'LeaderBoard',
      'primaryKey': 'id',

      'aliases': {
         'pathID': 'pathId'
      },

      'referenceMap': {
         'path': 'pathId'
      },

      'listReferences': {}
   },

   'Path': {
      'name': 'Path',
      'table': 'Path',
      'primaryKey': 'id',
      'aliases': {},
      'referenceMap': {},

      'listReferences': {
         'steps': 'pathID'
      }
   },

   'PathResult': {
      'name': 'PathResult',
      'table': 'PathResult',
      'primaryKey': 'id',

      'aliases': {
         'pathID': 'pathId',
         'userID': 'userId'
      },

      'referenceMap': {
         'path': 'pathId',
         'user': 'userId'
      },

      'listReferences': {}
   },

   'Proof': {
      'name': 'Proof',
      'table': 'Proof',
      'primaryKey': 'id',

      'aliases': {
         'stepID': 'stepId'
      },

      'referenceMap': {
         'step': 'stepId'
      },

      'listReferences': {}
   },

   'ProofResult': {
      'name': 'ProofResult',
      'table': 'ProofResult',
      'primaryKey': 'id',

      'aliases': {
         'pathResultID': 'pathResultId'
      },

      'referenceMap': {
         'pathResult': 'pathResultId'
      },

      'listReferences': {}
   },

   'Proximity': {
      'name': 'Proximity',
      'table': 'Proximity',
      'primaryKey': 'id',

      'aliases': {
         'stepID': 'stepId'
      },

      'referenceMap': {
         'step': 'stepId'
      },

      'listReferences': {}
   },

   'Score': {
      'name': 'Score',
      'table': 'Score',
      'primaryKey': 'id',

      'aliases': {
         'leaderBoardID': 'leaderBoardId',
         'userID': 'userId'
      },

      'referenceMap': {
         'leaderBoard': 'leaderBoardId',
         'user': 'userId'
      },

      'listReferences': {}
   },

   'Step': {
      'name': 'Step',
      'table': 'Step',
      'primaryKey': 'id',

      'aliases': {
         'stopBeaconID': 'stopBeaconId',
         'pathID': 'pathId'
      },

      'referenceMap': {
         'stopBeacon': 'stopBeaconId',
         'path': 'pathId'
      },

      'listReferences': {}
   },

   'User': {
      'name': 'User',
      'table': 'User',
      'primaryKey': 'id',
      'aliases': {},
      'referenceMap': {},
      'listReferences': {}
   }
};

exports.resolveMap = resolveMap;

exports.registerType = function registerType(type) {
   if (!resolveMap[type.name]) {
      throw new Error(
         'Cannot register type "' + type.name + '" - resolve map does not exist for that type'
      );
   }

   resolveMap[type.name].type = type;
};

exports.getType = function getType(type) {
   if (!resolveMap[type] || !resolveMap[type].type) {
      throw new Error('No type registered for type \'' + type + '\'');
   }

   return resolveMap[type].type;
};