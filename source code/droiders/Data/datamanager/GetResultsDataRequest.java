package Data.datamanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by andrea on 28/07/16.
 */
public class GetResultsDataRequest extends DataManager<Data.PathResult[]> {
   GetResultsDataRequest(Context cx, AbstractDataManagerListener<Data.PathResult[]> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected String queryForLocalData() {
      return "SELECT * FROM PathResult";
   }

   protected Data.PathResult[] parseFromLocal() {
      Data.PathResult[] results = new Data.PathResult[5];
      return results;
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getResults(cx, listener);
   }

   protected Data.PathResult[] parseFromUrlRequest(JSONObject response){
      try {
         JSONArray array = response.getJSONArray("array");
         Data.PathResult[] results = new Data.PathResult[array.length()];
         for(int i=0; i<array.length(); i++) {
            JSONObject result = array.getJSONObject(i);
            JSONArray proofArray = result.getJSONArray("proofResults");
            ArrayList<Data.ProofResult> proofs = new ArrayList<Data.ProofResult>();
            for(int j=0; j<proofArray.length(); j++) {
               JSONObject proof = proofArray.getJSONObject(i);
               GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
               try {
                  startCalendar.setTime(new SimpleDateFormat().parse(proof.getString("startTime")));
                  endCalendar.setTime(new SimpleDateFormat().parse(proof.getString("endTime")));
                  proofs.add(new Data.ProofResult(proof.getInt("proofID"), startCalendar, endCalendar, proof.getInt("score")));
               } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
                  proofs.add(new Data.ProofResult(proof.getInt("proofID"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), proof.getInt("score")));
               }
            }
            GregorianCalendar startCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
            try {
               startCalendar.setTime(new SimpleDateFormat().parse(result.getString("startTime")));
               endCalendar.setTime(new SimpleDateFormat().parse(result.getString("endTime")));
               results[i] = new Data.PathResult(result.getInt("pathID"), result.getString("pathName"), result.getString("buildingName"), startCalendar, endCalendar, result.getInt("totalScore"), proofs);
            } catch(ParseException e) { //non segnalo l'errore con il listener perché non vale la pena compromettere l'operazione per una data che non viene creata correttamente
               results[i] = new Data.PathResult(result.getInt("proofID"), result.getString("pathName"), result.getString("buildingName"), new GregorianCalendar(2000, 1, 1, 0, 0, 0), new GregorianCalendar(2000, 1, 1, 0, 0, 0), result.getInt("totalScore"), proofs);
            }
         }
         return results;
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of PathResults request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new Data.PathResult[0];
      }
   }

   protected void updateLocalData(Data.PathResult[] data){

   }

   protected String getUpdateLocalDataQuery() {
      return "";
   }
}
