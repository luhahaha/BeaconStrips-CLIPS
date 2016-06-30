package client.request;

abstract class AbstractListener { //la classe astratta da cui derivano i listener, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
	public abstract onResponse(JSONObject response);
	public abstract onError(VolleyError error);
}
