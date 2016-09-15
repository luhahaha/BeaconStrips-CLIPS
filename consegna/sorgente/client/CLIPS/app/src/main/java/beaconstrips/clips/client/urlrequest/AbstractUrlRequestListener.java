package beaconstrips.clips.client.urlrequest;

import org.json.JSONObject;

/**
 * @file AbstractUrlRequestListener.java
 * @date 01/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * interfaccia dei listener dell'urlrequest, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
 */
public interface AbstractUrlRequestListener {
   void onResponse(JSONObject response);

   void onError(ServerError error);
}
