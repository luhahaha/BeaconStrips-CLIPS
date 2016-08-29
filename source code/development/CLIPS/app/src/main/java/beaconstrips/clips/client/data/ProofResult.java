package beaconstrips.clips.client.data;

import java.util.GregorianCalendar;

/**
 * @file ProofResult.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che rappresenta i dati del risultato di una prova svolta dall'utente
 */

public class ProofResult {
   public final int id;
   public final GregorianCalendar startTime;
   public final GregorianCalendar endTime;
   public final int score;

   public ProofResult(int id, GregorianCalendar startTime, GregorianCalendar endTime, int score) {
      this.id = id;
      this.startTime = startTime;
      this.endTime = endTime;
      this.score = score;
   }


   //TODO correggere calcolo durata
   //Nota Cenze: il calcolo è corretto, è la trasformazione con dateFormat che è errata perché aggiunge un'ora di troppo, basta aggiungere l'istruzione:
   //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
   //dove dateFormat è l'oggetto di tipo SimpleDateFormat usato.
   public long getDuration() {
      return endTime.getTime().getTime() - startTime.getTime().getTime();
   }
}