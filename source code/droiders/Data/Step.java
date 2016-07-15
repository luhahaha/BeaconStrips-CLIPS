package Data;

import java.util.List;

public class Step {
   public final Beacon stopBeacon;
   public final List<Proximity> proximities;
   public final Proof proof;
   public final int id;

   Step(Beacon stopBeacon, List<Proximity> proximities, Proof proof, int id) {
      this.stopBeacon=stopBeacon;
      this.proximities=proximities;
      this.proof=proof;
      this.id=id;
   }
}
