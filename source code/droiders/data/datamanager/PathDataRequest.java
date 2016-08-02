package data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrea on 27/07/16.
 */
public class PathDataRequest extends DataManager<data.Path> {
   int pathID;

   public PathDataRequest(Context cx, int pathID, AbstractDataManagerListener<data.Path> listener) {
      super(cx, DataManager.CachePolicy.AlwaysReplaceLocal, listener);
      this.pathID = pathID;
      execute();
   }

   protected data.Path parseFromLocal() {
      return new DBHandler(cx).readPath(pathID);
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getPath(cx, pathID, listener);
   }

   protected data.Path parseFromUrlRequest(JSONObject response) {
      try {
         ArrayList<data.Step> steps = new ArrayList<>();
         JSONArray arraySteps = response.getJSONArray("steps");
         for(int i=0; i<arraySteps.length(); i++) {
            JSONObject step = arraySteps.getJSONObject(i);
            JSONObject beaconObject = step.getJSONObject("stopBeacon");
            data.Beacon beacon = new data.Beacon(beaconObject.getInt("id"), beaconObject.getString("UUID"), beaconObject.getInt("major"), beaconObject.getInt("minor"));
            ArrayList<data.Proximity> proximities = new ArrayList<>();
            JSONArray arrayProximities = step.getJSONArray("proximities");
            for(int j=0; j<arrayProximities.length(); j++) {
               JSONObject proximityObject = arrayProximities.getJSONObject(j);
               JSONObject proximityBeaconObject = proximityObject.getJSONObject("beacon");
               data.Beacon proximityBeacon = new data.Beacon(proximityBeaconObject.getInt("id"), proximityBeaconObject.getString("UUID"), proximityBeaconObject.getInt("major"), proximityBeaconObject.getInt("minor"));
               proximities.add(new data.Proximity(proximityBeacon, proximityObject.getDouble("percentage"), proximityObject.getString("textToDisplay")));
            }
            JSONObject proofObject = step.getJSONObject("proof");
            data.Proof proof = new data.Proof(proofObject.getString("title"), proofObject.getString("instructions"), proofObject.getJSONObject("algorithmData"), proofObject.getJSONObject("testData"));
            steps.add(new data.Step(beacon, proximities, proof));
         }
         return new data.Path(response.getInt("id"), response.getString("startingMessage"), response.getString("rewardMessage"), steps);
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of Path request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return null;
      }
   }

   protected void updateLocalData(data.Path data){
      new DBHandler(cx).updatePath(data);
   }
}
