/**
 * @file BuildingSearchActivity.java
 * @date 2016-07-11
 * @version 1.60
 * @author Matteo Franco
 * Sì occupa della gestione dell'activity di ricerca degli edifici
 **/

package beaconstrips.clips.client.viewcontroller.building;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.pathprogress.GPSListener;
import beaconstrips.clips.client.pathprogress.PathProgressMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;


public class BuildingSearchActivity extends MenuActivity {

   private TextView distanceText;
   private SeekBar distance;
   private CheckBox searchRadius;
   private Button showResult;
   private ListView results;
   private Intent i;
   final private String TAG = "BuildingSearchActivity";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_building_search);

      distanceText = (TextView) findViewById(R.id.distanceKmText);
      distance = (SeekBar) findViewById(R.id.kmFilter);
      searchRadius = (CheckBox) findViewById(R.id.checkRadius);
      showResult = (Button) findViewById(R.id.searchBuilding);
      results = (ListView) findViewById(R.id.buildingResults);

      distance.setEnabled(false);
      distanceText.setText(String.valueOf(distance.getProgress()));
      distanceText.setVisibility(View.INVISIBLE);
      results.setVisibility(View.INVISIBLE);

      i = new Intent(getApplicationContext(), BuildingActivity.class);

      setSeekBarSignal();
      setCheckBoxSignal();
      setButton();
      setItems();
   }

   void setSeekBarSignal() {
      distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            distanceText.setText(String.valueOf(distance.getProgress()));
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {
            return;
         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {
            return;
         }
      });
   }

   void setCheckBoxSignal() {
      searchRadius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (searchRadius.isChecked()) {
               distance.setEnabled(true);
               distanceText.setVisibility(View.VISIBLE);
            } else {
               distance.setEnabled(false);
               distanceText.setVisibility(View.INVISIBLE);
            }
         }
      });
   }

   private void setButton() {
      showResult.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            final Context context = getApplicationContext();
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean gpsEnabled = false;
            boolean networkEnabled = false;

            try {
               gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {

            }

            try {
               networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            // controllo se sono stati forniti i permessi per la geolocalizzazione
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23) {
               // se l'utente ha rifiutato di dare i permessi mostro la motivazione per cui servono
               if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                  showMessageOKCancel("Per cercare gli edifici più vicini a te abbiamo bisogno della tua posizione.",
                          new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                        0);
                             }
                          });
               }
               // la prima volta mostro la richiesta semplice
               else {
                  requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
               }
            }

            if (!gpsEnabled && !networkEnabled) {

               new AlertDialog.Builder(BuildingSearchActivity.this)
                       .setMessage("Servizi di localizzazione non attivi")
                       .setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                             Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                             i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(i);
                          }
                       })
                       .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                          }
                       })
                       .show();
            } else {
               PathProgressMaker.getCoordinates(getApplicationContext(), new GPSListener() {
                  @Override
                  public void onResponse(double latitude, double longitude) {

                     int radius = 10;

                     if (searchRadius.isChecked()) {
                        radius = distance.getProgress();
                     }

                     i.putExtra("distance", radius);
                     i.putExtra("latitude", latitude);
                     i.putExtra("longitude", longitude);

                     DataRequestMaker.getBuildings(getApplicationContext(), latitude, longitude, radius, true, new AbstractDataManagerListener<Building[]>() {
                        @Override
                        public void onResponse(Building[] response) {

                           ListView listView = (ListView) findViewById(R.id.buildingResults);

                           String[] buildingsName = new String[response.length];
                           String[] pathsNumber = new String[response.length];

                           if(response.length == 0) {
                              Toast.makeText(BuildingSearchActivity.this, "Nessun risultato trovato, prova ad incrementare il raggio di ricerca", Toast.LENGTH_LONG).show();
                           }
                           for (int i = 0; i < response.length; ++i) {
                              buildingsName[i] = response[i].name;
                              pathsNumber[i] = String.valueOf(response[i].pathsInfos.size());
                           }
                           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_building, R.id.buildingName, buildingsName);
                           listView.setAdapter(arrayAdapter);

                           results.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(ServerError error) {

                           Log.e(TAG, error.errorCode + " " + error.debugMessage + " " + error.userMessage);
                           new AlertDialog.Builder(BuildingSearchActivity.this)
                                   .setTitle("Avviso")
                                   .setMessage("Si è verificato un errore nello scaricamento dei dati. Controlla la tua connessione ad internet e riprova.")
                                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {

                                      }
                                   })
                                   .setIcon(android.R.drawable.ic_dialog_alert)
                                   .show();
                        }
                     });
                  }

                  @Override
                  public void onError(ServerError error) {
                     Log.e(TAG, "Not getting position");
                     new AlertDialog.Builder(BuildingSearchActivity.this)
                             .setTitle("Avviso")
                             .setMessage("Si è verificato un errore nell'acquisire la posizione, " +
                                     "assicurati che la geolocalizzazione sia attiva ed impostata su \"Alta precisione\". Riprova.")
                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .show();
                  }

               });

            }
         }
      });
   }

   private void setItems() {
      final ListView results = (ListView) findViewById(R.id.buildingResults);
      results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View view,
                                 int position, long id) {
            String buildingName = (results.getItemAtPosition(position)).toString();
            Log.i("Building name pressed", buildingName);
            i.putExtra("buildingName", buildingName);
            startActivity(i);
         }
      });
   }

   private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
      new android.support.v7.app.AlertDialog.Builder(BuildingSearchActivity.this)
           .setMessage(message)
           .setPositiveButton("OK", okListener)
           .setNegativeButton("Cancel", null)
           .create()
           .show();
   }
}