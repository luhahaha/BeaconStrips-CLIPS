public class Proof {
   public final String instructions;
   public final String title;
   private final String LinearScoringAlgorithm scoringAlghorithm;
   private final String testData; //descrizione inizale del tipo di test
   public final int id;

   Proof(String title, String instructions, JSONObject algorithmData, String testData, int id) { //da verificare se poi viene effettivamente usato un JSONObject
      this.title=title;
      this.instructions=instructions;
      this.scoringAlgorithm=ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto
      this.testData=testData;
      this.id=id;
   }
}
