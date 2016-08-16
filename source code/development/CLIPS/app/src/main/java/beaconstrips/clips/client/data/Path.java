package beaconstrips.clips.client.data;

import java.util.ArrayList;

import beaconstrips.clips.client.pathprogress.RawBeacon;

/**
 * @file Path.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe i dati di un percorso, ovvero il messaggio di inizio, quello di fine e l'elenco delle stazioni
 */
public class Path {
   public final int id;
   public final String startingMessage;
   public final String rewardMessage;
   public final ArrayList<Step> steps;

   public Path(int id, String startingMessage, String rewardMessage, ArrayList<Step> steps) {
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
