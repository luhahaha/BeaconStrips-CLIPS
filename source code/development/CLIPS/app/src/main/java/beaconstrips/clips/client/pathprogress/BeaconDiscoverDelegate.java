package beaconstrips.clips.client.pathprogress;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

/**
 * @file BeaconDiscoverDelegate.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 * interfaccia che che serve per la notifica degli eventi: si è entrati nel raggio di un beacon e si è usciti dal raggio di un beacon
 */

public interface BeaconDiscoverDelegate {
   void didFoundBeacon(IBeaconDevice beacon);
   void didMoveFromBeacon(RawBeacon beacon);
    void pathEnded(double totalScore);
}
