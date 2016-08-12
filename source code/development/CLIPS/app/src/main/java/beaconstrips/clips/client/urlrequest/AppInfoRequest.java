package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * @file AppInfoRequest.java
 * @date 02/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server delle informazioni sull'applicazione
 */
class AppInfoRequest extends URLRequest {
   AppInfoRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "appinfo", null, false, listener);
      execute(ResponseExpected.Object);
   }
}


