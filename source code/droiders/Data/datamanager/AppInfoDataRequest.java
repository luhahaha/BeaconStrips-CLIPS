package Data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrea on 26/07/16.
 */
public class AppInfoDataRequest extends DataManager<Data.AppInfo> { //La matrice ha 2 colonne, una con il nome che sarà associato allo SharedPreferences e uno con il contenuto
   AppInfoDataRequest(Context cx, AbstractDataManagerListener<Data.AppInfo> listener) {
      super(cx, CachePolicy.AlwaysReplaceLocal, listener);
      execute();
   }

   protected String queryForLocalData() {return "";}

   protected Data.AppInfo parseFromLocal() {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      Data.AppInfo appInfo = new Data.AppInfo(preferences.getString("description",""), preferences.getString("supportemail",""), preferences.getString("websiteURL",""), preferences.getString("discoveryUUID",""));
      return appInfo;
   }

   protected void getRemoteData(urlrequest.AbstractUrlRequestListener listener) {
      urlrequest.RequestMaker.getAppInfo(cx, listener);
   }

   protected Data.AppInfo parseFromUrlRequest(JSONObject response){
      try {
         Data.AppInfo appInfo = new Data.AppInfo(response.getString("description"), response.getString("supportemail"), response.getString("websiteURL"), response.getString("discoveryUUID"));
         return appInfo;
      } catch(JSONException e) {
         listener.onError(new urlrequest.ServerError(1002, "Error on parsing the response JSON after the execution of AppInfo request", "")); //per sicurezza, per evitare inconsistenze. L'errore 1002 indica un errore in fase di parsing della risposta;
         return new Data.AppInfo("", "", "", "");
      }
   }

   protected void updateLocalData(Data.AppInfo data){
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cx);
      SharedPreferences.Editor editor = preferences.edit();
      editor.remove("description");
      editor.putString("description", data.description);
      editor.remove("supportemail");
      editor.putString("supportemail", data.supportEmail);
      editor.remove("websiteURL");
      editor.putString("websiteURL", data.website);
      editor.remove("discoveryUUID");
      editor.putString("discoveryUUID", data.UUID);
      editor.apply();
   }

   protected String getUpdateLocalDataQuery() {return "";}
}
