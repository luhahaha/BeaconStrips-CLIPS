package Data;

import org.json.JSONObject;

public class ScoringAlgorithmFactory { //il nom e della classe e quello del file sono da cambiare
   public static LinearScoringAlgorithm createScoringAlgorithm(JSONObject algorithmData) { //TODO: da verificare anche che venga effettivamente usato un JSONObject
      return new LinearScoringAlgorithm(5, 5, 5, 5, 5, 5); //TODO funzione da finire con la lettura del JSON e relativa inizializzazione della classe
   }
}
