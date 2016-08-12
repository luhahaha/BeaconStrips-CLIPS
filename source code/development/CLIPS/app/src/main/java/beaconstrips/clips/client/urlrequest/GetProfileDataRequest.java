package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * @file GetProfileDataRequest.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server dei dati del profilo dell'utente
 */
public class GetProfileDataRequest extends URLRequest {
   GetProfileDataRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "userData", null, true, listener); //Nota: username e password vengono modificati solo se non sono vuoti, in caso contrario viene fatta comunque la chiamata ma il server non cambia il rispettivo dato
      execute(ResponseExpected.Object);
   }
}
