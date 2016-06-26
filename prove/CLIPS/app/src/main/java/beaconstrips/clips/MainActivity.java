package beaconstrips.clips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import client.urlrequest.URLRequest;


public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      TextView serverResponse;
      setContentView(R.layout.activity_main);
      serverResponse = (TextView) this.findViewById(R.id.serverResponse); //associa l'oggetto serverResponse alla TextView definita in activity_main.xml consentendone la modifica
      URLRequest test = new URLRequest(this);



   }
}
