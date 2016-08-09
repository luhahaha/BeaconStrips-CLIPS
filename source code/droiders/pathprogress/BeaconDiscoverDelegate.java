package pathprogress;

interface BeaconDiscoverDelegate {
   void didFoundBeacon(RawBeacon beacon);
   void didMoveFromBeacon(RawBeacon beacon);
}
