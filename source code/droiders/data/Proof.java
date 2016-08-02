package data;

import org.json.JSONObject;

public class Proof {
   public final String instructions;
   public final String title;
   public final LinearScoringAlgorithm scoringAlgorithm;
   public final TestCollection tests;

   public Proof(String title, String instructions, JSONObject algorithmData, JSONObject testData) { //da verificare se poi viene effettivamente usato un JSONObject
      this.title = title;
      this.instructions = instructions;
      this.scoringAlgorithm = ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto

      tests = new ConcreteTestFactory().createTestCollection(testData);
   }
}
