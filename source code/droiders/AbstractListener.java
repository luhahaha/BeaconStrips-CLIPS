package urlrequest;

import org.json.JSONObject;

abstract class AbstractListener { //la classe astratta da cui derivano i listener, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
	public abstract void onResponse(JSONObject response);
	public abstract void onError(VolleyError error);
}
