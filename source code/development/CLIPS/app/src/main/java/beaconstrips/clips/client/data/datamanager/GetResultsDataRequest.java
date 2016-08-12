package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GetResultsDataRequest.java
 * @date 28/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da DataManager dove vengono implementati tutti i metodi necessari per ottenere, dal server o, in caso avvenga qualche errore, dal database locale, tutti i risultati ottenuti dall'utente
 */
public class GetResultsDataRequest extends DataManager<PathResult[]> {
   GetResultsDataRequest(Context cx, AbstractDataManagerListener<PathResult[]> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected PathResult[] parseFromLocal() {
      return new DBHandler(cx).readPathResults();
   }

   protected void getRemoteData(AbstractUrlRequestListener listener) {
      RequestMaker.getResults(cx, listener);
   }

   protected PathResult[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         PathResult[] results = new PathResult[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject result = array.getJSONObject(i);
            JSONArray proofArray = result.getJSONArray("proofResults");
            ArrayList<ProofResult> proofs = new ArrayList<ProofResult>();
            for(int j=0; j<proofArray.length(); j++) {
               JSONObject proof = proofArray.getJSONObject(i);
               GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
               try {
                  startCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(proof.getString("startTime")));
                  endCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(proof.getString("endTime")));
                  proofs.add(new ProofResult(proof.getInt("proofID"), startCalendar, endCalendar, proof.getInt("score")));
               } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
                  proofs.add(new ProofResult(proof.getInt("proofID"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), proof.getInt("score")));
               }
            }
            GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
            try {
               startCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(result.getString("startTime")));
               endCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(result.getString("endTime")));
               results[i] = new PathResult(result.getInt("pathID"), result.getString("pathName"), result.getString("buildingName"), startCalendar, endCalendar, result.getInt("totalScore"), proofs);
            } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
               results[i] = new PathResult(result.getInt("proofID"), result.getString("pathName"), result.getString("buildingName"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), result.getInt("totalScore"), proofs);
            }
         }
         return results;
      } catch(JSONException e) {
         listener.onError(new ServerError(1002, "Error on parsing the response JSON after the execution of PathResults request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new PathResult[0];
      }
   }

   protected void updateLocalData(PathResult[] data){
         new DBHandler(cx).updatePathResults(data);
   }
}
