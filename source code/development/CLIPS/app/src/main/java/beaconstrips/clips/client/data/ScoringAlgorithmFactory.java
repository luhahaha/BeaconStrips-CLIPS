package beaconstrips.clips.client.data;

import org.json.JSONObject;

/**
 * @file ScoringAlgorithmFactory.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe in cui viene impostato l'algoritmo di calcolo usato nelle prove in base ai dati contenuti nel JSON
 */
public class ScoringAlgorithmFactory { //il nom e della classe e quello del file sono da cambiare
   public static LinearScoringAlgorithm createScoringAlgorithm(JSONObject algorithmData) { //TODO: da verificare anche che venga effettivamente usato un JSONObject
      return new LinearScoringAlgorithm(5, 5, 5, 5, 5, 5); //TODO funzione da finire con la lettura del JSON e relativa inizializzazione della classe
   }
}
