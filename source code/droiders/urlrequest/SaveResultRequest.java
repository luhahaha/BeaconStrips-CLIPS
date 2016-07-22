package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Data.PathResult;
import Data.ProofResult;

/**
 * Created by andrea on 02/07/16.
 */

/**
 * classe che inizializza l'URLRequest per inviare i risultati salvati del percorso appena giocato
 */
public class SaveResultRequest extends URLRequest {
   SaveResultRequest(Context cx, PathResult result, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "", getBody(result), true, listener);
      execute();
   }

   //dato che la chiamata super() deve precedere qualunque altra istruzione costruisco il body in questo metodo, è statico perché altrimenti non può essere usato nel super()
   static JSONObject getBody(PathResult result) {
      JSONArray array = new JSONArray();
      for(ProofResult element : result.proofResults) {
         JSONObject item = new JSONObject();
         try {
            item.put("proofID", element.proof.id);
            item.put("startTime", element.startTime);
            item.put("endTime", element.endTime);
            item.put("score", element.score);
         } catch (JSONException e) {
            e.printStackTrace();
         }
         array.put(item);
      }
      JSONObject body = new JSONObject();
      try {
         body.put("pathID", result.path.id);
         body.put("startTime", result.startTime);
         body.put("endTime", result.endTime);
         body.put("proofResults", array);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      return body;
   }
}
