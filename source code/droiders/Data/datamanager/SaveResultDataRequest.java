package Data.datamanager;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import Data.PathResult;

/**
 * Created by andrea on 20/07/16.
 */
public class SaveResultDataRequest extends SaveDataManager { //andrà sicuramente modificata o questa classe o SaveDataManager, altrimenti in execute non finirà mai result
   private PathResult result;

   public SaveResultDataRequest(Context cx, PathResult result, AbstractDataManagerListener<Data.ResponseStatus> listener) {
      super(cx, listener);
      this.result = result;
      execute();
   }

   void execute() {
      urlrequest.RequestMaker.saveResult(cx, result, new urlrequest.AbstractUrlRequestListener() {
         public void onResponse(JSONObject response) { //se bisogna salvare in locale le operazioni si possono fare qui
            returnResponse(response);
         }

         public void onError(VolleyError error) {
            returnError(error);
         }
      });
   }
}
