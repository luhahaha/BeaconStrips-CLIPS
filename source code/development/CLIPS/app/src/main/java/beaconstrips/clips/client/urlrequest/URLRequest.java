package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import beaconstrips.clips.client.data.datamanager.LoginManager;

/**
 * @file URLRequest.java
 * @date 01/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe base per effettuare le richieste al server, al cui interno vengono definite le chiamate in base ai parametri forniti dalle classi derivate
 */

class URLRequest {
   protected enum ResponseExpected { //in base a questo tipo viene effettuata una chiamata differente
      Object, Array
   }

   private Context cx;
   private int httpMethod;
   private String url;
   private JSONObject body;
   private boolean requiresAuthentication; //authentication è vera quando viene richiesta l'autenticazione, e quindi viene aggiunto l'header con il token dell'utente, falso altrimenti
   private AbstractUrlRequestListener listener;
   private static boolean buildingJSONError = false;

   URLRequest(Context cx, int httpMethod, String url, JSONObject body, boolean authentication, AbstractUrlRequestListener listener) {
      this.cx = cx;
      this.httpMethod = httpMethod;
      this.url = url;
      this.body = body;
      requiresAuthentication = authentication;
      this.listener = listener;
      if(buildingJSONError==true) {
         listener.onError(new ServerError(1003, "Error on building JSONObject", "")); //per sicurezza, per evitare inconsistenze. L'errore 1003 indica un errore in fase di costruzione del JSONObject
      }
   }

   protected static void signalError() {buildingJSONError = true;} //chiamato quando c'è necessità di segnalare un errore in fase di costruzione

   //effettua la chiamata al server
   void execute(ResponseExpected type) {
      RequestQueue queue = Volley.newRequestQueue(cx);
      if(type==ResponseExpected.Object) {
         //qui viene creata la richiesta da fare
         JsonObjectRequest request = objectRequest();
         queue.add(request); //qui viene aggiunta la richiesta appena creata
      } else if(type==ResponseExpected.Array) {
         //qui viene creata la richiesta da fare
         JsonRequest<JSONArray> request = arrayRequest();
         queue.add(request); //qui viene aggiunta la richiesta appena creata
      }
   }

   private JsonObjectRequest objectRequest() {
      return new JsonObjectRequest(httpMethod, url, body,
            new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                  listener.onResponse(response);
               }
            }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            ServerError errorData;
            try {
               String errorBody = new String(error.networkResponse.data, "utf-8");
               JSONObject body = new JSONObject(errorBody);
               errorData = new ServerError(error.networkResponse.statusCode, body.optString("debugMessage"), body.optString("userMessage"));
            } catch (JSONException e) {
               errorData = new ServerError(1000, "Error server isn't a JSON, the error code is " + error.networkResponse.statusCode, ""); //per sicurezza, per evitare inconsistenze. L'errore 1000 indica un errore in fase di parsing dell'errore del server causato dal formato errato dei dati ricevuti.
            } catch (UnsupportedEncodingException encError) {
               errorData = new ServerError(1001, "utf-8 encoding isn't supported", ""); //per sicurezza, per evitare inconsistenze. L'errore 1001 indica un errore in fase di parsing dell'errore del server causato da un difetto dell'applicazione o del dispositivo.
            }
            listener.onError(errorData);
         }
      }) {
         @Override
         public Map<String, String> getHeaders() throws AuthFailureError { //l'errore è di volley
            HashMap<String, String> headers = new HashMap<>();
            if (requiresAuthentication == true)
               headers.put("Authorization", LoginManager.sharedManager(cx).getToken()); //qui viene aggiunto agli headers il token, se esso non esiste, ovvero l'utente non è loggato, il valore salvato sarà una stringa vuota, cioè l'autenticazione fallirà sicuramente
            return headers;
         }
      };
   }

   private JsonRequest<JSONArray> arrayRequest() {
      if(body==null)
         body=new JSONObject();
      return new JsonRequest<JSONArray>(httpMethod, url, body.toString(),
            new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                  try {
                     JSONObject transformedResponse = new JSONObject();
                     transformedResponse.put("array", response);
                     listener.onResponse(transformedResponse);
                  } catch(JSONException e) {
                     listener.onError(new ServerError(1004, "Error on reading JSON response", "")); //per sicurezza, per evitare inconsistenze. L'errore 1004 indica un errore in fase di parsing del JSONArray
                  }
               }
            }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            ServerError errorData;
            try {
               String errorBody = new String(error.networkResponse.data, "utf-8");
               JSONObject body = new JSONObject(errorBody);
               errorData = new ServerError(error.networkResponse.statusCode, body.optString("debugMessage"), body.optString("userMessage"));
            } catch (JSONException e) {
               errorData = new ServerError(1000, "Error response isn't a JSON, the error code is " + error.networkResponse.statusCode, ""); //per sicurezza, per evitare inconsistenze. L'errore 1000 indica un errore in fase di parsing dell'errore del server causato dal formato errato dei dati ricevuti.
            } catch (UnsupportedEncodingException encError) {
               errorData = new ServerError(1001, "utf-8 encoding isn't supported", ""); //per sicurezza, per evitare inconsistenze. L'errore 1001 indica un errore in fase di parsing dell'errore del server causato da un difetto dell'applicazione o del dispositivo.
            }
            listener.onError(errorData);
         }
      }) {
         @Override
         public Map<String, String> getHeaders() throws AuthFailureError { //l'errore è di volley
            HashMap<String, String> headers = new HashMap<>();
            if (requiresAuthentication == true) {
               headers.put("Authorization", LoginManager.sharedManager(cx).getToken()); //qui viene aggiunto agli headers il token
            }
            return headers;
         }
         @Override
         protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            try {
               String jsonString = new String(response.data,
                     HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
               return Response.success(new JSONArray(jsonString),
                     HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
               return Response.error(new ParseError(e));
            } catch (JSONException je) {
               return Response.error(new ParseError(je));
            }
         }
      };
   }
}

