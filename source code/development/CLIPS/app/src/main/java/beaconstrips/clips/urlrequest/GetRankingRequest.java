package beaconstrips.clips.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 29/07/16.
 */
public class GetRankingRequest extends URLRequest {
   GetRankingRequest(Context cx, int pathID, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "ranking", setBody(pathID), true, listener);
      execute(ResponseExpected.Array);
   }

   private static JSONObject setBody(int pathID) {
      JSONObject body = new JSONObject();
      try {
         body.put("pathID", pathID);
      } catch (JSONException e) {
         signalError();
      }
      return body;
   }
}
