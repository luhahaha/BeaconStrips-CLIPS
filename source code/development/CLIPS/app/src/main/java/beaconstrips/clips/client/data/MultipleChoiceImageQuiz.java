package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file MultipleChoiceImageQuiz.java
 * @date 15/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una domanda a risposta multipla con 4 scelte possibili, in cui il quesito posto contiene un'immagine
 */
public class MultipleChoiceImageQuiz extends MultipleChoiceTextQuiz implements Serializable{
   public final String image; //contiene il nome dell'immagine

   public MultipleChoiceImageQuiz(String helpText, String instructions, String trueResponse, String[] falseResponse, String image) {
      super(helpText, instructions, trueResponse, falseResponse);
      this.image = image;
   }
}
