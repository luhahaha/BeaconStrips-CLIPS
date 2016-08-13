package beaconstrips.clips.client.data;

import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * @file PathResult.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe che rappresenta i dati dei risultati ottenuti in un percorso svolto dall'utente
 */

public class PathResult {
   public final int pathID;
   public final String pathName;
   public final String buildingName;
   public final GregorianCalendar startTime;
   public final GregorianCalendar endTime;
   public final int totalScore;
   public final ArrayList<ProofResult> proofResults;

   public PathResult(int pathID, String pathName, String buildingName, GregorianCalendar startTime, GregorianCalendar endTime, int totalScore, ArrayList<ProofResult> proofResults) {
      this.pathID = pathID;
      this.pathName = pathName;
      this.buildingName = buildingName;
      this.startTime = startTime;
      this.endTime = endTime;
      this.totalScore = totalScore;
      this.proofResults = proofResults;
   }
}