package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * @file GetResultsRequest.java
 * @date 02/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe derivata da URLRequest dove vengono impostati i dati per effettuare la richiesta al server dei risultati ottenuti dall'utente
 */
public class GetResultsRequest extends URLRequest {
   GetResultsRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "pathsresults", null, true, listener);
      execute(ResponseExpected.Array);
   }
}
