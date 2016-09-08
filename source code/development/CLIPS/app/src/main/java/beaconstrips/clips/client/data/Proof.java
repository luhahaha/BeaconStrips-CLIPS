package beaconstrips.clips.client.data;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @file Proof.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una prova, contiene quindi un test, un algoritmo di calcolo del punteggio, un titolo e una spiegazione
 */
public class Proof implements Serializable{
   public final int id;
   public final String instructions;
   public final String title;
   public final LinearScoringAlgorithm scoringAlgorithm;
   public final Test test;
   public final JSONObject algorithmJSON;
   public final JSONObject testJSON;

   public Proof(int id, String title, String instructions, JSONObject algorithmData, JSONObject testData) { //da verificare se poi viene effettivamente usato un JSONObject
      this.id = id;
      this.title = title;
      this.instructions = instructions;
      this.scoringAlgorithm = ScoringAlgorithmFactory.createScoringAlgorithm(algorithmData); //da verificare se risulta tutto corretto
      this.algorithmJSON = algorithmData;
      this.testJSON = testData;

      test = TestBuilder.createTest(testData);
   }
}
