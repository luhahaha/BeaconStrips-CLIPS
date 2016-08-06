package beaconstrips.clips.client.data;

import java.util.List;

public class Path {
   public final int id;
   public final String startingMessage;
   public final String rewardMessage;
   public final List<Step> steps;

   public Path(int id, String startingMessage, String rewardMessage, List<Step> steps) {
      this.id = id;
      this.startingMessage = startingMessage;
      this.rewardMessage = rewardMessage;
      this.steps = steps;
   }
}
