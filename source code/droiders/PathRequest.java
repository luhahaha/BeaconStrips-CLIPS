package urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by andrea on 02/07/16.
 */

//inizializza l'URLRequest per richiedere i dati del percorso selezionato, comprese tutte le prove da giocare
public class PathRequest extends URLRequest {
   PathRequest(Context cx, int pathID, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.POST, URLDataConstants.baseURL + "path/" + pathID, null, false, listener);
      execute();
   }
}
