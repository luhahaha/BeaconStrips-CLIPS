package urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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
      execute(ResponseExpected.Object);
   }

   //dato che la chiamata super() deve precedere qualunque altra istruzione costruisco il body in questo metodo, è statico perché altrimenti non può essere usato nel super()
   static JSONObject getBody(PathResult result) {
      JSONArray array = new JSONArray();
      for(ProofResult element : result.proofResults) {
         JSONObject item = new JSONObject();
         try {
            item.put("proofID", element.id);
            item.put("startTime", DateFormat.getDateInstance(DateFormat.SHORT).format(element.startTime.getTime()));
            item.put("endTime", DateFormat.getDateInstance(DateFormat.SHORT).format(element.endTime.getTime()));
            item.put("score", element.score);
         } catch (JSONException e) {
            signalError();
         }
         array.put(item);
      }
      JSONObject body = new JSONObject();
      try {
         body.put("pathID", result.pathID);
         body.put("pathName", result.pathName);
         body.put("buildingName", result.buildingName);
         body.put("totalScore", result.totalScore);
         body.put("startTime", DateFormat.getDateInstance(DateFormat.SHORT).format(result.startTime.getTime()));
         body.put("endTime", DateFormat.getDateInstance(DateFormat.SHORT).format(result.endTime.getTime()));
         body.put("proofResults", array);
      } catch (JSONException e) {
         signalError();
      }
      return body;
   }
}
