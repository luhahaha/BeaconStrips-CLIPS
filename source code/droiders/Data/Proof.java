package Data;

import org.json.JSONObject;

public class Proof {
   public final String instructions;
   public final String title;
   private final LinearScoringAlgorithm scoringAlgorithm;
   public final int id;

   Proof(String title, String instructions, JSONObject algorithmData, int id) { //da verificare se poi viene effettivamente usato un JSONObject
      this.title=title;
      this.instructions=instructions;
      this.scoringAlgorithm=ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto
      this.id=id;
   }
}
