package data;

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

   public boolean equal(Beacon a, RawBeacon b){
      return a.UUID == b.UUID && a.major == b.major && a.minor == b.minor;
   }

   public Proximity searchProximity(RawBeacon rawBeacon){
      for(int i=0; i < steps.size(); i++){
         for (int j = 0; j < steps.get(i).proximities.size(); j++){
            if(equal(steps.get(i).proximities.get(j).beacon,rawBeacon))
               return steps.get(i).proximities.get(j);
         }
      }
      return null;
   }
}
