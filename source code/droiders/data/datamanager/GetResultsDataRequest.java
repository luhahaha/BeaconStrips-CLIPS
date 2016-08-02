package data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by andrea on 28/07/16.
 */
public class GetResultsDataRequest extends DataManager<data.PathResult[]> {
   GetResultsDataRequest(Context cx, AbstractDataManagerListener<data.PathResult[]> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected data.PathResult[] parseFromLocal() {
      return new DBHandler(cx).readPathResults();
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getResults(cx, listener);
   }

   protected data.PathResult[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         data.PathResult[] results = new data.PathResult[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject result = array.getJSONObject(i);
            JSONArray proofArray = result.getJSONArray("proofResults");
            ArrayList<data.ProofResult> proofs = new ArrayList<data.ProofResult>();
            for(int j=0; j<proofArray.length(); j++) {
               JSONObject proof = proofArray.getJSONObject(i);
               GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
               try {
                  startCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(proof.getString("startTime")));
                  endCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(proof.getString("endTime")));
                  proofs.add(new data.ProofResult(proof.getInt("proofID"), startCalendar, endCalendar, proof.getInt("score")));
               } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
                  proofs.add(new data.ProofResult(proof.getInt("proofID"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), proof.getInt("score")));
               }
            }
            GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
            try {
               startCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(result.getString("startTime")));
               endCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(result.getString("endTime")));
               results[i] = new data.PathResult(result.getInt("pathID"), result.getString("pathName"), result.getString("buildingName"), startCalendar, endCalendar, result.getInt("totalScore"), proofs);
            } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
               results[i] = new data.PathResult(result.getInt("proofID"), result.getString("pathName"), result.getString("buildingName"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), result.getInt("totalScore"), proofs);
            }
         }
         return results;
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of PathResults request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new data.PathResult[0];
      }
   }

   protected void updateLocalData(data.PathResult[] data){
      for(int i=0; i<data.length; i++){
         new DBHandler(cx).updatePathResults(data[i]);
      }
   }
}
