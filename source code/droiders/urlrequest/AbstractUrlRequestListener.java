package urlrequest;

import org.json.JSONObject;

//la classe astratta da cui derivano i listener dell'urlrequest, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
public abstract class AbstractUrlRequestListener {
   public abstract void onResponse(JSONObject response);

   public abstract void onError(ServerError error);
}