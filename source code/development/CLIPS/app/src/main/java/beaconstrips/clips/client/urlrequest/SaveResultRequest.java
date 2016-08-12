package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;

/**
 * @file SaveResultRequest.java
 * @date 02/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server di salvare i dati del percorso appena svolto
 */
public class SaveResultRequest extends URLRequest {
   SaveResultRequest(Context cx, PathResult result, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "pathsresults", setBody(result), true, listener);
      execute(ResponseExpected.Object);
   }

   static JSONObject setBody(PathResult result) {
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
