package urlrequest;

import com.android.volley.Request;

/**
 * Created by Enrico on 01/07/2016.
 */


//classe che inizializza l'URLRequest in modo da poter effettuare la richiesta per ottenere sia l'UUID dei beacon sia le stringhe delle ingo generali dell'app
class AppInfoRequest extends URLRequest {

   //nel costruttore vengono creati il JSON (che in questo caso non c'è) e tutti gli altri dati, infine inizializzo con super() l'URLRequest
   AppInfoRequest(AbstractListener listener){
      super(Request.Method.GET, URLDataConstants.baseURL + "", null, false, listener); //NOTA: l'url e' da finire

      //effettuo la richiesta, lo inserisco qui visto che viene sempre chiamato dai metodi di RequestMaker (senno' basta inserirlo nei metodi stessi)
      execute();
   }
}
