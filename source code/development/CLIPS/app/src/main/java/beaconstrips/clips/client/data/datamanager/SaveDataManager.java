package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file SaveDataManager.java
 * @date 20/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe astratta dove sono definite le operazioni generali per il salvataggio dei dati nel server, lasciando alle classi derivate la definizione delle operazioni più specifiche
 */
public abstract class SaveDataManager {
   protected final AbstractDataManagerListener<Boolean> listener;
   protected final Context cx;

   public SaveDataManager(Context cx, AbstractDataManagerListener<Boolean> listener) {
      this.cx = cx;
      this.listener = listener;
   }

   abstract void execute();

   protected void returnResponse() {
         listener.onResponse(true);
   }

   protected void returnError(ServerError error) {
      listener.onError(error);
   }
}
