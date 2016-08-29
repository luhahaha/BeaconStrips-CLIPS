package beaconstrips.clips.client.data;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @file PathProgress.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che rappresenta i dati del progresso di un percorso svolto dall'utente
 */

public class PathProgress {
   private final Path path;
   private final GregorianCalendar startTime;
   private GregorianCalendar endTime;
   private ArrayList<ProofResult> proofResults = new ArrayList<>();

   public PathProgress(Path path, GregorianCalendar startTime) {
      this.path = path;
      this.startTime = startTime;
   }

   public Path getPath() {
      return path;
   }

   public GregorianCalendar getStartTime() {
      return startTime;
   }

   public GregorianCalendar getEndTime() {
      return endTime;
   }

   public void setEndTime(GregorianCalendar endTime) {
      this.endTime = endTime;
   }

   //TODO correggere calcolo durata
   //Nota Cenze: il calcolo è corretto, è la trasformazione con dateFormat che è errata perché aggiunge un'ora di troppo, basta aggiungere l'istruzione:
   //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
   //dove dateFormat è l'oggetto di tipo SimpleDateFormat usato.
   public long getDuration() {
      return endTime.getTime().getTime() - startTime.getTime().getTime();
   }

   public void addProofResult(ProofResult proofResult) {
      proofResults.add(proofResult);
   }

   public List<ProofResult> getProofResults() {
      return proofResults;
   }

   public int getTotalScore() {
      int score = 0;
      for (int i = 0; i < proofResults.size(); ++i) {
         score += proofResults.get(i).score;
      }
      return score;
   }

}