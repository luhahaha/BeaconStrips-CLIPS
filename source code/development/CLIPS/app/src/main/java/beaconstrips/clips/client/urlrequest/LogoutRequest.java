package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * @file LogoutRequest.java
 * @date 05/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server di logout.
 */
public class LogoutRequest extends URLRequest {
   LogoutRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "logout", null, true, listener);
      execute(ResponseExpected.Object);
   }
}
