package urlrequest;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

//Questa classe è la superclasse che permette di comunicare con il server, il metodo execute() imposta la chiamata al server basandosi sulle variabili istanziate dal costruttore. Le sue sottoclassi semplicemente creano il body e impostano le variabili a seconda delle necessità tramite il costruttore di URLRequest. Il metodo execute() sarà usato da RequestMaker all'interno delle chiamate effettuate (o nelle sottoclassi).

//NOTA: il contratto della classe si conclude con la richiesta effettuata al server, si arrangia poi il Listener a gestire la risposta sia in caso positivo sia quando avviene un errore

//ALTRA NOTA: per semplicita' ho inserito per ora l'URLRequest, l'AppInfoRequest e il RequestMaker in un unico file, successivamente bisognera' dividerli nei vari file necessari

class URLRequest {
	private Request.Method httpMethod; //da verificare se cosi' funziona, altrimenti bisogna capire se puo' funzionare in un altro modo, male che vada si fa un enum usando uno switch nell'execute()
	private String url; //contiene l'url per la chiamata al server
	private JSONObject body; //da verificare se effettivamente useremo questo tipo di oggetto
	//qui va eventualmente inserita la callback
	private AbstractListener listener;

	void execute() { //effettua la chiamata al server, sarà chiamato da RequestMaker, il tipo di ritorno sara' poi la callback (Listener o del tipo che definiamo noi)
		RequestQueue queue = Volley.newRequestQueue(this);
		//ora viene creata la richiesta da fare
		JsonObjectRequest request = new JsonObjectRequest(httpMethod, url, body,
			new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
				//Mostra la stringa response, sfrutto lo standard output generale per evitare di dover definire altre strutture, questa istruzione sarà sicuramente sostituita con qualcos'altro (piu' probabilmente sara' eliminata)
					System.out.println("La risposta è: "+ response.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) { //come in onResponse la riga sotto quasi sicuramente sara' eliminata
				System.out.println("Niente da fare, qualcosa non va.");
				listener.onError(error);
			}
		});
		queue.add(request); //qui viene aggiunta la richiesta appena creata
	}

	//DA AGGIUNGERE NON APPENA LoginManager VIENE IMPLEMENTATA
	//void addHeader() {
	//	body.put("Authentication", Data.datamanager.LoginManager.sharedManager().getToken()); //qui viene aggiunto agli headers il token
	//}

	URLRequest(int httpMethod, String url, JSONObject body, boolean authentication, AbstractListener listener) { //il costruttore inizializza i campi dati. La callback è standard per tutti, quindi non e' richiesta come input per il costruttore. authentication è vera quando viene richiesta l'autenticazione, e quindi viene aggiunto l'header con il token dell'utente, falso altrimenti
		this.httpMethod = httpMethod; //vengono inizializzati tutti i dati in base a quelli immessi nel costruttore
		this.url = url;
		this.body = body;
		//DA AGGIUNGERE NON APPENA LoginManager VIENE IMPLEMENTATA
		//if(authentication == true) { //se è richiesta l'autenticazione allora il token viene caricato nell'header del JSONObject body
		//	addHeader();
		//}
		this.listener = listener;
	}
}

class AppInfoRequest extends URLRequest { //classe che inizializza l'URLRequest in modo da poter effettuare la richiesta per ottenere sia l'UUID dei beacon sia le stringhe delle ingo generali dell'app
	AppInfoRequest(AbstractListener listener){ //nel costruttore vengono creati il JSON (che in questo caso non c'è) e tutti gli altri dati, infine inizializzo con super() l'URLRequest
		super(Request.Method.GET, URLDataConstants.baseURL + "", null, false, listener); //NOTA: l'url e' da finire
		execute(); //effettuo la richiesta, lo inserisco qui visto che viene sempre chiamato dai metodi di RequestMaker (senno' basta inserirlo nei metodi stessi)
	}
}
