package beaconstrips.clips.client.urlrequest;

import org.json.JSONObject;

//la classe astratta da cui derivano i listener dell'urlrequest, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
public interface AbstractUrlRequestListener {
   void onResponse(JSONObject response);

   void onError(ServerError error);
}
