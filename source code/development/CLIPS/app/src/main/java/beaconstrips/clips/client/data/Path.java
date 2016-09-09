package beaconstrips.clips.client.data;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.io.Serializable;
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
public class Path implements Serializable {
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

   public Proximity searchProximity(IBeaconDevice beacon,int index){
       for (int j = 0; j < steps.get(index).proximities.size(); j++){
           if(beacon.getProximityUUID().equals(java.util.UUID.fromString(steps.get(index).proximities.get(j).beacon.UUID)) && beacon.getMajor() == steps.get(index).proximities.get(j).beacon.major
                   && beacon.getMinor() == steps.get(index).proximities.get(j).beacon.minor)
               return steps.get(index).proximities.get(j);
         }
      return null;
   }
}
