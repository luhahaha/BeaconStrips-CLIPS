package data;

/**
 * Created by andrea on 15/07/16.
 */
public class TrueFalseImageQuiz extends TrueFalseTextQuiz {
   public final String image; //contiene il nome dell'immagine

   public TrueFalseImageQuiz(String helpText, String instructions, boolean response, String image) {
      super(helpText, instructions, response);
      this.image = image;
   }
}
