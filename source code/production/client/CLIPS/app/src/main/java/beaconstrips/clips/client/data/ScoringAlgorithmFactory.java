package beaconstrips.clips.client.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @file ScoringAlgorithmFactory.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe in cui viene impostato l'algoritmo di calcolo usato nelle prove in base ai dati contenuti nel JSON
 */
public class ScoringAlgorithmFactory implements Serializable{ //il nom e della classe e quello del file sono da cambiare
   public static LinearScoringAlgorithm createScoringAlgorithm(JSONObject algorithmData) {
      try {
         return new LinearScoringAlgorithm(algorithmData.getJSONObject("data").getInt("minScore"), algorithmData.getJSONObject("data").getInt("maxScore"), algorithmData.getJSONObject("data").getDouble("minTime"), algorithmData.getJSONObject("data").getDouble("maxTime"), algorithmData.getJSONObject("data").getDouble("timeWeight"), algorithmData.getJSONObject("data").getDouble("accuracyWeight"));
      } catch (JSONException e) {
         return new LinearScoringAlgorithm(0, 0, 0, 0, 0, 0);
      }
   }
}
