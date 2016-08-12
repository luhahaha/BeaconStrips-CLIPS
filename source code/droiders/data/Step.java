package data;

import java.util.List;
import java.util.ListIterator;

public class Step {
   public final Beacon stopBeacon;
   public final List<Proximity> proximities;
   public final Proof proof;

   public Step(Beacon stopBeacon, List<Proximity> proximities, Proof proof) {
      this.stopBeacon = stopBeacon;
      this.proximities = proximities;
      this.proof = proof;
   }

   public Bool equal(Beacon a, RawBeacon b){
     return a.UUID == b.UUID && a.major == b.major && a.minor == b.minor;
   }

   public Proximity searchProximity(RawBeacon rawBeacon){
     for (int i = 0; i < proximities.size(); i++){
       if(equal(proximities.get(i).beacon,rawBeacon)
       return proximities.get(i);
     }
     return null;
   }
}
