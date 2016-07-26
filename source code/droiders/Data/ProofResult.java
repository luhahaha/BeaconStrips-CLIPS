package Data;

import java.util.Date;

public class ProofResult {
   public final Proof proof;
   public final Date startTime;
   public final Date endTime;
   public final int score;

   public ProofResult(Proof proof, Date startTime, Date endTime, int score) {
      this.proof = proof;
      this.startTime = startTime;
      this.endTime = endTime;
      this.score = score;
   }


   //TODO correggere calcolo durata
   public long getDuration() {
      return endTime.getTime() - startTime.getTime();
   }
}