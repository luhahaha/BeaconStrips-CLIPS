package Data;

/**
 * Created by andrea on 15/07/16.
 */
public abstract class AbstractQuiz {
   String instructions; //Nota: anche per un'immagine serve una descrizione di cosa deve fare l'utente

   public AbstractQuiz(String instructions) {
      this.instructions=instructions;
   }
}
