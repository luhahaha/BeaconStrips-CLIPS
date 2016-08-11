package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
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
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.MenuActivity;

//TODO rendere scrollabile la list view


public class BuildingSearchActivity extends MenuActivity {

    TextView distanceText;
    SeekBar distance;
    CheckBox searchRadius;
    Button showResult;
    ListView results;

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


        //TODO add as row search building
        setSeekBarSignal();
        setCheckBoxSignal();
        setButton();
        setItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_drawer, menu);
        return true;
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
                DataRequestMaker.getBuildings(getApplicationContext(), 45, 11, 10, new AbstractDataManagerListener<Building[]>() {
                    @Override
                    public void onResponse(Building[] response) {
                        Log.i("Building", "" + response.length);

                        //TODO limitare risultati
                        ListView listView = (ListView)findViewById(R.id.buildingResults);

                        String [] buildingsName = new String[response.length];
                        for(int i = 0; i < response.length; ++i) {
                            buildingsName[i] = response[i].name;
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_building, R.id.buildingName, buildingsName);
                        listView.setAdapter(arrayAdapter);

                        //TODO aggiungere numero percorsi
                        // ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.row_building, R.id.textView6, array);
                        //listView.setAdapter(arrayAdapter2);
                    }

                    @Override
                    public void onError(ServerError error) {
                        System.out.println(error.errorCode + " " + error.debugMessage + " " + error.userMessage);
                        Log.e("Error", "not working");
                    }
                });
                results.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setItems() {
        final ListView results = (ListView)findViewById(R.id.buildingResults);
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String buildingName = (results.getItemAtPosition(position)).toString();
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("buildingName", buildingName);
                startActivity(i);

            }
        });
    }
}
