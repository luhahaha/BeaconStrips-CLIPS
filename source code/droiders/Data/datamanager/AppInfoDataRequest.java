package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrea on 26/07/16.
 */
public class AppInfoDataRequest extends DataManager<HashMap<String, String>> { //La matrice ha 2 colonne, una con il nome che sarà associato allo SharedPreferences e uno con il contenuto
   AppInfoDataRequest(Context cx, AbstractDataManagerListener<HashMap<String, String>> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected String queryForLocalData() {return "";}

   protected HashMap<String, String> parseFromLocal() {
      HashMap<String, String> map = new HashMap<>();
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      map.put("UUID", preferences.getString("description",""));
      map.put("UUID", preferences.getString("supportemail",""));
      map.put("UUID", preferences.getString("websiteURL",""));
      map.put("UUID", preferences.getString("discoveryUUID",""));
      return map;
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getAppInfo(cx, listener);
   }

   protected HashMap<String, String> parseFromUrlRequest(JSONObject response){
      HashMap<String, String> map = new HashMap<>();
      try {
         map.put("description", response.getString("description"));
         map.put("supportemail", response.getString("supportemail"));
         map.put("websiteURL", response.getString("websiteURL"));
         map.put("discoveryUUID", response.getString("discoveryUUID"));
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of AppInfo request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
      }
      return map;
   }

   protected void updateLocalData(HashMap<String, String> data){
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      SharedPreferences.Editor editor = preferences.edit();
      editor.remove("description");
      editor.putString("description", data.get("description"));
      editor.remove("supportemail");
      editor.putString("supportemail", data.get("supportemail"));
      editor.remove("websiteURL");
      editor.putString("websiteURL", data.get("websiteURL"));
      editor.remove("discoveryUUID");
      editor.putString("discoveryUUID", data.get("discoveryUUID"));
      editor.apply();
   }

   protected String getUpdateLocalDataQuery() {return "";}
}
