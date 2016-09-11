/**
 * @file AccountActivity.java
 * @date 2016-07-14
 * @version 1.30
 * @author Viviana Alessio
 * Si occupa di gestire l'activity per cambiare i dati dell'utente autenticato
 */

package beaconstrips.clips.client.viewcontroller.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import beaconstrips.clips.R;
import beaconstrips.clips.client.urlrequest.AbstractUrlRequestListener;
import beaconstrips.clips.client.urlrequest.RequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class ChangeProfileActivity extends AppCompatActivity {

   private EditText newUsername;
   private EditText oldPassword;
   private EditText newPassword;
   private EditText repeatNewPassword;
   private TextInputLayout shortPassword;
   private TextInputLayout unmatchingPassword;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_change_profile);
      newUsername = (EditText) findViewById(R.id.new_username_login);
      oldPassword = (EditText) findViewById(R.id.old_password_login);
      newPassword = (EditText) findViewById(R.id.new_password_login);
      repeatNewPassword = (EditText) findViewById(R.id.repeat_new_password_login);
      shortPassword = (TextInputLayout) findViewById(R.id.new_password_label);
      unmatchingPassword = (TextInputLayout) findViewById(R.id.repeat_new_password_label);
      setButton();
   }

   void setButton() {
      Button change = (Button) findViewById(R.id.change_profile_button);
      if (change != null) {
         change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String u = newUsername.getText().toString();
               final String op = oldPassword.getText().toString();
               final String np = newPassword.getText().toString();
               final String npr = repeatNewPassword.getText().toString();
               if (checkFields(np, npr) & checkLenght(np)) {
                  RequestMaker.changeProfileData(getApplicationContext(), u, op, np, new AbstractUrlRequestListener() {
                     @Override
                     public void onResponse(JSONObject response) {
                        Log.e("change", "done");
                     }

                     @Override
                     public void onError(ServerError error) {
                        Log.e("change", "doesn't worked");
                     }
                  });
                  // dopo che ha cambiato i dati di accesso invito l'utente ad accedere con i nuovi dati
                  Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                  startActivity(i);
               }

            }
         });
      }
   }
   // TODO: controlli interfaccia grafica

   boolean checkFields(String np, String npr){
      // controllo se la nuova password ha più di 6 caratteri controllo se la nuova password è stata inserita la seconda volta correttamente
      if(np.equals(npr)) {
         return true;
      }
      else {
         unmatchingPassword.setErrorEnabled(true);
         unmatchingPassword.setError("Le password non combaciano");
         Log.e("ins", "pwd diverse");
         return false;
      }
   }

   boolean checkLenght(String p){
      if(p.length() > 6){
         return true;
      }
      else {
         shortPassword.setErrorEnabled(true);
         shortPassword.setError("Nuova password troppo corta");
         Log.v("ins", "troppo corta");
         return false;
      }
   }
}

