package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Proof {
   public final String instructions;
   public final String title;
   public final LinearScoringAlgorithm scoringAlgorithm;
   public final TestCollection tests;

   public Proof(String title, String instructions, JSONObject algorithmData, JSONArray testData) { //da verificare se poi viene effettivamente usato un JSONObject
      this.title = title;
      this.instructions = instructions;
      this.scoringAlgorithm = ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto

      for(int i=0; i<testData.length(); i++) {
         //TODO: aggiungere la chiamata al metodo dell'AbstractFactory che crea il test e aggiungere il test alla lista
         tests.add();
      }
   }
}
