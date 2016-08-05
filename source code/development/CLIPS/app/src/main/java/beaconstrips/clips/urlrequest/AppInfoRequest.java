package beaconstrips.clips.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by Enrico on 01/07/2016.
 */


//classe che inizializza l'URLRequest in modo da poter effettuare la richiesta per ottenere sia l'UUID dei beacon sia le stringhe delle info generali dell'app
class AppInfoRequest extends URLRequest {

   //nel costruttore vengono creati il JSON (che in questo caso non c'è) e tutti gli altri dati, infine inizializzo con super() l'URLRequest
   AppInfoRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "appinfo", null, false, listener);
      execute(ResponseExpected.Object);
   }
}


