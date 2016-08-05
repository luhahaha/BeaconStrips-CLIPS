package beaconstrips.clips.data;

import java.util.GregorianCalendar;
import java.util.List;

public class PathProgress {
   private final Path path;
   private final GregorianCalendar startTime;
   private GregorianCalendar endTime;
   private List<ProofResult> proofResults;

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