package client.urlrequest;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//Questa classe è la superclasse che permette di comunicare con il server, il metodo execute() imposta la chiamata al server basandosi sulle variabili istanziate dal costruttore. Le sue sottoclassi semplicemente creano il body e impostano le variabili a seconda delle necessità tramite il costruttore di URLRequest. Il metodo execute() sarà usato da RequestMaker all'interno delle chiamate effettuate.

//NOTA: per le callback bisogna capire come funzionano i Listener di volley, quindi probabilmente non servira' implementarle ma solo gestirle

class URLRequest {
	private final Request.Method httpMethod; //da verificare se cosi' funziona, altrimenti bisogna capire se puo' funzionare in un altro modo, male che vada si fa un enum usando uno switch nell'execute()
	private final String url; //contiene l'url per la chiamata al server
	private final Map<String, String> headers; //contiene gli headers per la chiamata al server
	private final JSONObject body; //da verificare se effettivamente useremo questo tipo di oggetto
	private final URLRequestCallback callback; //da verificare se effettivamente useremo questo tipo di oggetto
	private final boolean requiresAuthentication; //true se viene richiesta l'autenticazione, false altrimenti

	void execute() { //effettua la chiamata al server, sarà chiamato da RequestMaker, il tipo di ritorno sara' poi la callback (Listener o del tipo che definiamo noi)
		RequestQueue queue = Volley.newRequestQueue(this); //non so se in realtà questo campo dati sia uno di quelli dichiarati esternamente, ne dubito comunque
		//ora viene creata la richiesta da fare
		JsonObjectRequest Request = new JsonObjectRequest(httpMethod, url, null,
				new.Response.Listener<JSONObject>() { //Hic sunt leones, ovvero da qui ho fatto una copia ma non ho un'idea precisa di cosa stia trattando, sicuramente sarà da rivedere e modificare
			@Override
			public void onResponse(JSONObject response) {
				//Mostra i primi 500 caratteri della stringa response, sfrutto lo standard output generale per evitare di dover definire altre strutture, questa istruzione sarà sicuramente sostituita con qualcos'altro
				System.out.println("La risposta è: "+ response.toString());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) { //qui la guida nel caso specifico del Json non aveva completato il metodo, probabilmente non sarà comunque il punto più difficile da gestire
				System.out.println("Niente da fare, qualcosa non va.");
			}
		}); //DA FINIRE/CORREGGERE
		queue.add(Request); //qui viene aggiunta la richiesta appena creata
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

public class DiscoveryUUID { //classe di esempio, per ritornare il dato inserisco il costruttore dell'oggetto e un metodo getter per il campo dati, in alternativa posso usare un metodo statico che effettua la richiesta dato che posso crearci l'oggetto stesso all'interno
	private String UUID; //qui verrà salvato il dato
	public String getUUID() {return UUID;} //per poter usare questo metodo la classe dev'essere già costruita e quindi UUID sarà sicuramente non vuoto
	DiscoveryUUID(){ //nel costruttore viene creato il JSON, viene inizializzata l'URLRequest e viene salvato il valore ottenuto con la chiamata al server

	}
}