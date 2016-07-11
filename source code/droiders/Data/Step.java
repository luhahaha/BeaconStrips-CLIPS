public class Step {
   public final Beacon stopBeacon;
   public final list<Proximity> proximities;
   public final Proof proof;
   public final int id;

   Step(Beacon stopBeacon, list<Proximity> proximities, Proof proof, int id) {
      this.stopBeacon=stopBeacon;
      this.proximities=proximities;
      this.proof=proof;
      int id;
   }
}
