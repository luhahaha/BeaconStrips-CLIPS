package urlrequest;

import com.android.volley.VolleyError;

import org.json.JSONObject;

//la classe astratta da cui derivano i listener, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
public abstract class AbstractListener {
	public abstract void onResponse(JSONObject response);
	public abstract void onError(VolleyError error);
}
