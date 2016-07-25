package Data.datamanager;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by andrea on 20/07/16.
 */
public abstract class SaveDataManager {
   protected final AbstractDataManagerListener<Boolean> listener;
   protected final Context cx;

   public SaveDataManager(Context cx, AbstractDataManagerListener<Boolean> listener) {
      this.cx = cx;
      this.listener = listener;
   }

   abstract void execute();

   void returnResponse(JSONObject response) {
         listener.onResponse(true);
   }

   void returnError(VolleyError error) {
      //try {
         //Se occorre si può trasformare il contenuto dell'error in un JSONObject usando i comandi in commento
         //String responseBody = new String(error.networkResponse.data, "utf-8");
         //JSONObject body = new JSONObject(responseBody);
         Data.ServerError errorData = new Data.ServerError(error.networkResponse.statusCode, error.getMessage()); //in alternativa ci sono i comandi error.networkResponse.statusCode per l'errore, error.networkResponse.toString() e error.getMessage() per le stringhe
         listener.onError(errorData);
      /*} catch (JSONException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException encError) {
        encError.printStackTrace();
      }*/
   }
}
