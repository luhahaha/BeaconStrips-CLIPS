package urlrequest;

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
Questa classe è la superclasse che permette di comunicare con il server, il metodo execute() imposta la chiamata al server basandosi sulle variabili istanziate dal costruttore. Le sue sottoclassi semplicemente creano il body e impostano le variabili a seconda delle necessità tramite il costruttore di URLRequest. Il metodo execute() sarà usato da RequestMaker all'interno delle chiamate effettuate (o nelle sottoclassi).

NOTA: il contratto della classe si conclude con la richiesta effettuata al server, si arrangia poi il Listener a gestire la risposta sia in caso positivo sia quando avviene un errore

ALTRA NOTA: per semplicita' ho inserito per ora l'URLRequest, l'AppInfoRequest e il RequestMaker in un unico file, successivamente bisognera' dividerli nei vari file necessari
*/

class URLRequest {
	private Context cx;
	private int httpMethod; //da verificare se cosi' funziona, altrimenti bisogna capire se puo' funzionare in un altro modo, male che vada si fa un enum usando uno switch nell'execute()
	private String url; //contiene l'url per la chiamata al server
	private JSONObject body; //da verificare se effettivamente useremo questo tipo di oggetto
	//qui va eventualmente inserita la callback
	private boolean requiresAuthentication;
	private URLRequestListener listener;

	//effettua la chiamata al server, sarà chiamato da RequestMaker, il tipo di ritorno sara' poi la callback (Listener o del tipo che definiamo noi)
	void execute() {
		RequestQueue queue = Volley.newRequestQueue(cx);
		//ora viene creata la richiesta da fare
		JsonObjectRequest request = new JsonObjectRequest(httpMethod, url, body,
			new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError(error);
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError { //l'errore è di volley
				HashMap<String, String> headers = new HashMap<>();
				if(requiresAuthentication==true)
					headers.put("Authorization", Data.datamanager.LoginManager.sharedManager().getToken()); //qui viene aggiunto agli headers il token
				return headers;
			}
		};
		queue.add(request); //qui viene aggiunta la richiesta appena creata
	}

	//il costruttore inizializza i campi dati. La callback è standard per tutti, quindi non e' richiesta come input per il costruttore.
	//authentication è vera quando viene richiesta l'autenticazione, e quindi viene aggiunto l'header con il token dell'utente, falso altrimenti
	URLRequest(Context cx, int httpMethod, String url, JSONObject body, boolean authentication, URLRequestListener listener) {
		this.cx=cx;
		this.httpMethod = httpMethod; //vengono inizializzati tutti i dati in base a quelli immessi nel costruttore
		this.url = url;
		this.body = body;
		requiresAuthentication=authentication;
		this.listener = listener;
	}


}





//la classe astratta da cui derivano i listener, onResponse e onError gestiscono le risposte rispettivamente quando viene ottenuta la risposta e quando viene ricevuto un errore
abstract class URLRequestListener implements Response.Listener<JSONObject> {
	public abstract void onResponse(JSONObject response);
	public abstract void onError(VolleyError error);
}

