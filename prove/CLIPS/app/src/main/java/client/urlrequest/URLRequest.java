package client.urlrequest;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Enrico on 26/06/2016.
 */
public class URLRequest {

   private final String serverURL;
   private final String errorURL;

   private RequestQueue m_queue;
   private String retString;




   public URLRequest(Context cxt){
      serverURL = "http://52.58.232.113:1234/servertest";
      errorURL = "http://52.58.232.113:1234/servertest/error";
      m_queue = Volley.newRequestQueue(cxt);
   }

   public void sendGET()
   {
      StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
              new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    setStringValue("Response is: "+ response.substring(0,500));
                 }
              }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            setStringValue("That didn't work!");
         }
      });

      m_queue.add(getRequest);
   }

   public void sendPOST()
   {
      StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
              new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    setStringValue("Response is: "+ response.substring(0,500));
                 }
              }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            setStringValue("That didn't work!");
         }
      });

      m_queue.add(getRequest);
   }

   public void sendError()
   {
      StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
              new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    setStringValue("Response is: "+ response.substring(0,500));
                 }
              }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            setStringValue("That didn't work!");
         }
      });

      m_queue.add(getRequest);
   }

   private void setStringValue(String value)
   {
      retString = value;
   }

}
