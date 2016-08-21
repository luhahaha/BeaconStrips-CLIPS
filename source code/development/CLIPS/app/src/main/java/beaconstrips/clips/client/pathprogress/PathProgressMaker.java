package beaconstrips.clips.client.pathprogress;

import android.content.Context;

/**
 * @file PathProgressMaker.java
 * @date 20/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che funge da interfaccia tra la richiesta di ottenere la posizione dell'utente con il GPS e il resto dell'applicazione
 */
public class PathProgressMaker {
   static void getCoordinates(Context cx, GPSListener listener) {
      new GPSManager(cx, listener);
   }
}
