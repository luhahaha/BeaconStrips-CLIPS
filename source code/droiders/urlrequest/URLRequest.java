package urlrequest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import Data.datamanager.LoginManager;

/**
 * Questa classe è la superclasse che permette di comunicare con il server, il metodo execute() imposta la chiamata al server basandosi sulle variabili istanziate dal costruttore. Le sue sottoclassi semplicemente creano il body e impostano le variabili a seconda delle necessità tramite il costruttore di URLRequest. Il metodo execute() sarà usato da RequestMaker all'interno delle chiamate effettuate (o nelle sottoclassi).
 * <p>
 * NOTA: il contratto della classe si conclude con la richiesta effettuata al server, si arrangia poi il Listener a gestire la risposta sia in caso positivo sia quando avviene un errore
 */

class URLRequest {
   private Context cx;
   private int httpMethod;
   private String url; //contiene l'url completo per la chiamata al server
   private JSONObject body;
   private boolean requiresAuthentication; //authentication è vera quando viene richiesta l'autenticazione, e quindi viene aggiunto l'header con il token dell'utente, falso altrimenti
   private AbstractUrlRequestListener listener;

   URLRequest(Context cx, int httpMethod, String url, JSONObject body, boolean authentication, AbstractUrlRequestListener listener) {
      this.cx = cx; //vengono inizializzati tutti i dati in base a quelli immessi nel costruttore
      this.httpMethod = httpMethod;
      this.url = url;
      this.body = body;
      requiresAuthentication = authentication;
      this.listener = listener;
   }

   //effettua la chiamata al server
   void execute() {
      RequestQueue queue = Volley.newRequestQueue(cx);
      //qui viene creata la richiesta da fare
      JsonObjectRequest request = new JsonObjectRequest(httpMethod, url, body,
            new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                  System.out.println("Ciao");
                  listener.onResponse(response);
               }
            }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            Data.ServerError errorData;
            try {
               String errorBody = new String(error.networkResponse.data, "utf-8");
               JSONObject body = new JSONObject(errorBody);
               errorData = new Data.ServerError(error.networkResponse.statusCode, body.optString("userMessage"), body.optString("debugMessage"));
            } catch (JSONException e) {
               errorData = new Data.ServerError(1000, "", ""); //per sicurezza, per evitare inconsistenze, anche se non dovrebbero esserci problemi, si potrebbero implementare i due messaggi successivamente o anche solo quello per l'utente. L'errore 1000 indica un errore in fase di parsing dell'errore del server
            } catch (UnsupportedEncodingException encError) {
               errorData = new Data.ServerError(1000, "", ""); //per sicurezza, per evitare inconsistenze, anche se non dovrebbero esserci problemi, si potrebbero implementare i due messaggi successivamente o anche solo quello per l'utente. L'errore 1000 indica un errore in fase di parsing dell'errore del server
            }
            listener.onError(errorData);
         }
      }) {
         @Override
         public Map<String, String> getHeaders() throws AuthFailureError { //l'errore è di volley
            HashMap<String, String> headers = new HashMap<>();
            if (requiresAuthentication == true)
               headers.put("Authorization", LoginManager.sharedManager(cx).getToken()); //qui viene aggiunto agli headers il token
            return headers;
         }
      };
      queue.add(request); //qui viene aggiunta la richiesta appena creata
   }
}

