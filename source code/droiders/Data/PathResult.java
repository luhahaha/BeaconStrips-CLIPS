package Data;

import java.util.GregorianCalendar;
import java.util.List;

public class PathResult {
   public final int pathID;
   public final String pathName;
   public final String buildingName;
   public final GregorianCalendar startTime;
   public final GregorianCalendar endTime;
   public final int totalScore;
   public final List<ProofResult> proofResults;

   public PathResult(int pathID, String pathName, String buildingName, GregorianCalendar startTime, GregorianCalendar endTime, int totalScore, List<ProofResult> proofResults) {
      this.pathID = pathID;
      this.pathName = pathName;
      this.buildingName = buildingName;
      this.startTime = startTime;
      this.endTime = endTime;
      this.totalScore = totalScore;
      this.proofResults = proofResults;
   }
}