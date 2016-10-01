package beaconstrips.clips.client.data.datamanager;

import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file AbstractDataManagerListener.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * interfaccia dei listener del datamanager, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
 */

public interface AbstractDataManagerListener<ModelObject> {
   void onResponse(ModelObject response);

   void onError(ServerError error);
}
