/**
 * @file BuildingActivity.java
 * @date 2016-07-12
 * @version 1.30
 * @author Matteo Franco
 * Permette di gestire l'activity dell'edificio
 */

package beaconstrips.clips.client.viewcontroller.building;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.BuildingAdapter;
import beaconstrips.clips.client.viewcontroller.utility.risultatoProva;

public class BuildingActivity extends AppCompatActivity {

   private ListView pathsResult;
   private TextView name;
   private TextView description;
   private TextView otherInfo;
   private TextView address;
   private TextView telephone;
   private TextView email;
   private TextView website;
   private TextView openingTime;
   private ArrayList<PathInfo> paths;
   String buildingName;
   Intent i;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_building);
      i = getIntent();
      buildingName = i.getStringExtra("buildingName");
      final int radius = i.getIntExtra("distance", 10);
      final double latitude = i.getDoubleExtra("latitude", 45);
      final double longitude = i.getDoubleExtra("longitude", 11);
      name = (TextView) findViewById(R.id.buildingName);
      name.setText(buildingName);
      address = (TextView) findViewById(R.id.buildingAddress);
      telephone = (TextView) findViewById(R.id.buildingTelephone);
      description = (TextView) findViewById(R.id.description);
      otherInfo = (TextView) findViewById(R.id.otherInfo);
      email = (TextView) findViewById(R.id.buildingEmail);
      website = (TextView) findViewById(R.id.buildingSite);
      openingTime = (TextView) findViewById(R.id.buildingOpeningTime);

      pathsResult = (ListView) findViewById(R.id.pathsResult);
      setButtons();
      setItems();
      DataRequestMaker.getBuildings(getApplicationContext(), latitude, longitude, radius, true, new AbstractDataManagerListener<Building[]>() { //TODO passare tramite intent coordinate e numero edifici
         @Override
         public void onResponse(Building[] response) {
            Log.i("Building", "" + response.length);

            //TODO limitare risultati

            boolean found = false;
            Building loadPaths = null;
            //TODO check se non trovato
            for (int i = 0; !found && i < response.length; ++i) {
               Log.i("Edifici", "" + response[i].name);
               address.setText(response[i].address);
               telephone.setText(response[i].telephone);
               description.setText(response[i].description);
               otherInfo.setText(response[i].otherInfos);
               email.setText(response[i].email);
               website.setText(response[i].websiteURL);
               openingTime.setText(response[i].openingTime);
               if (response[i].name.equals(buildingName)) {
                  loadPaths = response[i];
                  found = true;
               }
               Log.i("Found", "" + found);
            }

            if (loadPaths == null) {
               Log.i("Paths infos", "null");
               new AlertDialog.Builder(BuildingActivity.this)
                       .setTitle("Avviso")
                       .setMessage("C'è stato un errore nello scaricamento dei dati")
                       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {

                          }
                       })
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .show();
            }

            else {

               paths = loadPaths.getPathInfo();

               pathsResult.setAdapter(new BuildingAdapter(getApplicationContext(), paths));

               //TODO aggiungere numero percorsi

            }
         }

         @Override
         public void onError(ServerError error) {
            Log.e("Error", "not working");
         }
      });
      //TODO add all names of contacts
   }


   private void setButtons() {
      final Button openPaths = (Button) findViewById(R.id.pathsButton);
      if (openPaths != null) {
         openPaths.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Intent paths = new Intent(getApplicationContext(), PathsActivity.class);
               //startActivity(paths);
               //TODO add spinner for loading

            }
         });
      }
   }

   private void setItems() {
      pathsResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View view,
                                 int position, long id) {
            int pathId = Integer.parseInt((((TextView) view.findViewById(R.id.pathId)).getText()).toString()); //TODO recuperare id per recuperare informazioni path
            String pathName = ((TextView) view.findViewById(R.id.pathName)).getText().toString();
            i.setClass(getApplicationContext(), PathActivity.class);
            i.putExtra("pathId", pathId);
            i.putExtra("pathName", pathName);
            i.putExtra("pathDescription", paths.get(position).description);
            i.putExtra("pathTarget", paths.get(position).target);
            i.putExtra("pathEstimatedDuration", paths.get(position).estimatedDuration);
            i.putExtra("pathPosition", paths.get(position).position);
            startActivity(i);
         }
      });
   }


}
