/**
 * @file BuildingSearchActivity.java
 * @date 2016-07-11
 * @version 1.40
 * @author Matteo Franco
 * Sì occupa della gestione dell'activity di ricerca degli edifici
 **/

package beaconstrips.clips.client.viewcontroller.building;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.pathprogress.GPSListener;
import beaconstrips.clips.client.pathprogress.PathProgressMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;
import beaconstrips.clips.client.pathprogress.PathProgressMaker;

//TODO rendere scrollabile la list view


public class BuildingSearchActivity extends MenuActivity {

    TextView distanceText;
    SeekBar distance;
    CheckBox searchRadius;
    Button showResult;
    ListView results;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_search);

        i = new Intent(getApplicationContext(), BuildingActivity.class);
        distanceText = (TextView) findViewById(R.id.distanceKmText);
        distance = (SeekBar) findViewById(R.id.kmFilter);
        searchRadius = (CheckBox) findViewById(R.id.checkRadius);
        showResult = (Button) findViewById(R.id.searchBuilding);
        results = (ListView) findViewById(R.id.buildingResults);


        distance.setEnabled(false);
        distanceText.setText(String.valueOf(distance.getProgress()));
        distanceText.setVisibility(View.INVISIBLE);
        results.setVisibility(View.INVISIBLE);


        //TODO add as row search building
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
                Log.i("Distance", String.valueOf(distance.getProgress()));
            }
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
                return;
            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                return;
            }
        });
    }

    void setCheckBoxSignal() {
        searchRadius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(searchRadius.isChecked()) {
                    distance.setEnabled(true);
                    distanceText.setVisibility(View.VISIBLE);
                }
                else {
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
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {
                }

                try {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    new AlertDialog.Builder(BuildingSearchActivity.this)
                            .setMessage("Servizi di localizzazione non attivi")
                            .setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    // TODO Auto-generated method stub

                                }
                            })
                            .show();
                } else {
                    Log.i("Trying to call" , "");
                    PathProgressMaker.getCoordinates(getApplicationContext(), new GPSListener() {
                        @Override
                        public void onResponse(double latitude, double longitude) {
                            Log.i("Getting position", "");
                            Log.i("Latitude:", "" + latitude);
                            Log.i("Longitude:", "" + longitude);
                            int radius = 10;
                            if(searchRadius.isChecked()) {
                               radius = distance.getProgress();
                            }
                            i.putExtra("distance", radius);
                            i.putExtra("latitude", latitude);
                            i.putExtra("longitude", longitude);
                            DataRequestMaker.getBuildings(getApplicationContext(), latitude, longitude, radius, true, new AbstractDataManagerListener<Building[]>() {
                                @Override
                                public void onResponse(Building[] response) {

                                    //TODO limitare risultati
                                    ListView listView = (ListView) findViewById(R.id.buildingResults);

                                    String[] buildingsName = new String[response.length];
                                    for (int i = 0; i < response.length; ++i) {
                                        buildingsName[i] = response[i].name;
                                        Log.i("Building name", "" + response[i].name);
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_building, R.id.buildingName, buildingsName);
                                    listView.setAdapter(arrayAdapter);

                                    //TODO aggiungere numero percorsi
                                    // ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.row_building, R.id.textView6, array);
                                    //listView.setAdapter(arrayAdapter2);
                                    results.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(ServerError error) {
                                    System.out.println(error.errorCode + " " + error.debugMessage + " " + error.userMessage);
                                    Log.e("Error", "not working");
                                }
                            });
                        }

                        @Override
                        public void onError(ServerError error) {
                            Log.e("Error", "Not getting position");
                        }

                    });

                }
            }
        });
    }

    private void setItems() {
        final ListView results = (ListView)findViewById(R.id.buildingResults);
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
}
