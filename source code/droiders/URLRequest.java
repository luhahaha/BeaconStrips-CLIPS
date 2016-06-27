package client.urlrequest;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//NOTA: per le callback bisogna capire come funzionano i Listener di volley, quindi probabilmente non servira' implementarle ma solo gestirle

public class URLRequest {
	protected final Request.Method httpMethod; //da verificare se cosi' funziona, altrimenti bisogna capire se puo' funzionare in un altro modo, male che vada si fa un enum usando uno switch nell'execute()
	protected final String url; //contiene l'url per la chiamata al server
	protected final Map<String, String> headers; //contiene gli headers per la chiamata al server
	protected final JSONObject body; //da verificare se effettivamente useremo questo tipo di oggetto
	protected final URLRequestCallback callback; //da verificare se effettivamente useremo questo tipo di oggetto
	protected final boolean requiresAuthentication; //true se viene richiesta l'autenticazione, false altrimenti

	void execute() { //effettua la chiamata al server, sarà chiamato da RequestMaker, il tipo di ritorno sara' poi la callback (Listener o del tipo che definiamo noi)
		StringRequest Request = new StringRequest(http.method, url, ) //TODO
	}

	URLRequest(Request.method httpMethod, String url, Map<String, String> headers, JSONObject body, boolean authentication) { //il costruttore inizializza i campi dati. La callback è standard per tutti, quindi non e' richiesta come input per il costruttore
		this.httpMethod=httpMethod; //vengono inizializzati tutti i dati in base a quelli immessi nel costruttore
		this.url=url;
		this.headers=headers;
		this.body=body;
		//inizializzazione della callback
		requiresAuthentication=authentication;
	}
}
