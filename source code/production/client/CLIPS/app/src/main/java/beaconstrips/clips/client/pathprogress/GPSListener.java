package beaconstrips.clips.client.pathprogress;

import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GPSListener.java
 * @date 20/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe astratta del listener usato per richiedere le coordinate GPS.
 */
public interface GPSListener {
   void onResponse(double latitude, double longitude);
   void onError(ServerError error);
}
