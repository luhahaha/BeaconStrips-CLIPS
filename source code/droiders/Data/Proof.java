package Data;

import org.json.JSONObject;

public class Proof {
   public final String instructions;
   public final String title;
   public final int id;
   private final LinearScoringAlgorithm scoringAlgorithm;

   public Proof(String title, String instructions, JSONObject algorithmData, int id) { //da verificare se poi viene effettivamente usato un JSONObject
      this.title = title;
      this.instructions = instructions;
      this.scoringAlgorithm = ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto
      this.id = id;
   }
}
