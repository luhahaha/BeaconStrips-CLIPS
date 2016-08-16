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
}
