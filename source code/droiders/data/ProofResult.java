package data;

import java.util.GregorianCalendar;

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
   public long getDuration() {
      return endTime.getTime().getTime() - startTime.getTime().getTime();
   }
}