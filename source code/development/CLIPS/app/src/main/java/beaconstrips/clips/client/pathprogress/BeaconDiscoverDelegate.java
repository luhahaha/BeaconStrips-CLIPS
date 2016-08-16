package beaconstrips.clips.client.pathprogress;

/**
 * @file BeaconDiscoverDelegate.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 * interfaccia che che serve per la notifica degli eventi: si è entrati nel raggio di un beacon e si è usciti dal raggio di un beacon
 */

interface BeaconDiscoverDelegate {
   void didFoundBeacon(RawBeacon beacon);
   void didMoveFromBeacon(RawBeacon beacon);
}
