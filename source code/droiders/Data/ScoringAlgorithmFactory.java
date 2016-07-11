public class ScoringAlgorithmFactory { //il nom e della classe e quello del file sono da cambiare
   static LinearScoringAlgorithm createScoringAlgorithm(JSONObject algorithmData) { //TODO: da verificare anche che venga effettivamente usato un JSONObject
      return new LineaScoringAlgorithm();
   }
}
