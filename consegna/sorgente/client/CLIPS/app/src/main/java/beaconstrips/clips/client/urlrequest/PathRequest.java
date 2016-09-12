package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * @file PathRequest.java
 * @date 02/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server dei dati del percorso selezionato
 */
public class PathRequest extends URLRequest {
   PathRequest(Context cx, int pathID, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "path/" + pathID, null, false, listener);
      execute(ResponseExpected.Object);
   }
}
