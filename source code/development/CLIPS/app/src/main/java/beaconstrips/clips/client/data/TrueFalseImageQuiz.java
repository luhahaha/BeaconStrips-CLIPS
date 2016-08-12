package beaconstrips.clips.client.data;

/**
 * @file TrueFalseImageQuiz.java
 * @date 15/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta una domanda vero o falso, in cui viene usata un'immagine per porre il quesito
 */
public class TrueFalseImageQuiz extends TrueFalseTextQuiz {
   public final String image; //contiene il nome dell'immagine

   public TrueFalseImageQuiz(String helpText, String instructions, boolean response, String image) {
      super(helpText, instructions, response);
      this.image = image;
   }
}
