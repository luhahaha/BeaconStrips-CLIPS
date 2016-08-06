package beaconstrips.clips.client.urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by andrea on 22/07/16.
 */
public class GetProfileDataRequest extends URLRequest {
   GetProfileDataRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "userData", null, true, listener); //Nota: username e password vengono modificati solo se non sono vuoti, in caso contrario viene fatta comunque la chiamata ma il server non cambia il rispettivo dato
      execute(ResponseExpected.Object);
   }
}
