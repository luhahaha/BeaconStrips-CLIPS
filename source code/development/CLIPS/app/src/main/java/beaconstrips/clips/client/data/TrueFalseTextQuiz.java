package beaconstrips.clips.client.data;

import java.io.Serializable;

/**
 * @file TrueFalseTextQuiz.java
 * @date 15/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una domanda vero o falso
 */
public class TrueFalseTextQuiz extends AbstractQuiz implements Serializable{
   public final Boolean response; //Dato che la domanda è booleana basta un dato boolean per rappresentare la risposta

   public TrueFalseTextQuiz(String helpText, String instructions, boolean response) {
      super(helpText, instructions);
      this.response = response;
   }

   public boolean check(boolean userResponse) {
      return response == userResponse;
   }
}
