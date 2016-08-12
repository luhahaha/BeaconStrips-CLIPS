package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import org.json.JSONObject;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file SaveResultDataRequest.java
 * @date 20/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da SaveDataManager dove sono definite le operazioni necessarie per salvare nel server il risultato del percorso appena svolto dall'utente
 */
public class SaveResultDataRequest extends SaveDataManager {
   private PathResult result;

   public SaveResultDataRequest(Context cx, PathResult result, AbstractDataManagerListener<Boolean> listener) {
      super(cx, listener);
      this.result = result;
      execute();
   }

   void execute() {
      RequestMaker.saveResult(cx, result, new AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //se bisogna salvare in locale le operazioni si possono fare qui
            returnResponse();
         }

         public void onError(ServerError error) {
            returnError(error);
         }
      });
   }
}
