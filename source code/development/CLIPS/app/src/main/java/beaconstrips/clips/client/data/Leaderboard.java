package beaconstrips.clips.client.data;

import java.util.Date;
import java.util.List;

public class Leaderboard {
   public final PathInfo pathInfo;
   public final Date lastUpdate;
   public final int nPlayers;
   public final int maxScore;
   public final List<Score> scores;

   public Leaderboard(PathInfo pathInfo, Date lastUpdate, int nPlayers, int maxScore, List<Score> scores) {
      this.pathInfo = pathInfo;
      this.lastUpdate = lastUpdate;
      this.nPlayers = nPlayers;
      this.maxScore = maxScore;
      this.scores = scores;
   }
}