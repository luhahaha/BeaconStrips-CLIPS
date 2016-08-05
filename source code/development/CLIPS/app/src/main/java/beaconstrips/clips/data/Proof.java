package beaconstrips.clips.data;

import org.json.JSONObject;

public class Proof {
   public final int id;
   public final String instructions;
   public final String title;
   public final LinearScoringAlgorithm scoringAlgorithm;
   public final Test test;

   public Proof(int id, String title, String instructions, JSONObject algorithmData, JSONObject testData) { //da verificare se poi viene effettivamente usato un JSONObject
      this.id = id;
      this.title = title;
      this.instructions = instructions;
      this.scoringAlgorithm = ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto

      test = TestBuilder.createTest(testData);
   }
}
