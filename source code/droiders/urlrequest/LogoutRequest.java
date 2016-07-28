package urlrequest;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by Enrico on 05/07/2016.
 */
public class LogoutRequest extends URLRequest {
   LogoutRequest(Context cx, AbstractUrlRequestListener listener) {
      super(cx, Request.Method.GET, URLDataConstants.baseURL + "logout", null, true, listener);
      execute(ResponseExpected.Object);
   }
}
