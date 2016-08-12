package beaconstrips.clips.client.data;

/**
 * @file AbstractQuiz.java
 * @date 15/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe astratta che rappresenta una domanda generica, che contiene quindi il quesito, salvato dentro a instructions, e un testo di aiuto, salvato dentro a helpText, visualizzato solo dopo un certo periodo di tempo per aiutare l'utente
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractQuiz {
   public final String helpText;
   public final String instructions;

   public AbstractQuiz(String helpText, String instructions) {
      this.helpText = helpText;
      this.instructions = instructions;
   }
}
