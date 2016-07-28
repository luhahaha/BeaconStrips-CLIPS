package urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere l'elenco dei percorsi effettuati dall'utente
public class GetResultsRequest extends URLRequest {
   GetResultsRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "pathsresults", null, true, listener);
      execute(ResponseExpected.Array);
   }
}
