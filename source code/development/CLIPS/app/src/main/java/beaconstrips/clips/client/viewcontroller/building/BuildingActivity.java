package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;

public class BuildingActivity extends AppCompatActivity {

    private ListView pathsResult;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Intent i = getIntent();
        String valueName = i.getStringExtra("pathName");
        name = (TextView) findViewById(R.id.buildingName);
        name.setText(valueName);
        pathsResult = (ListView) findViewById(R.id.pathsResult);
        setButtons();
        setItems();
        DataRequestMaker.getBuildings(getApplicationContext(), 45, 11, 10, new AbstractDataManagerListener<Building[]>() { //TODO passare tramite intent coordinate e numero edifici
            @Override
            public void onResponse(Building[] response) {
                Log.i("Building", "" + response.length);

                //TODO limitare risultati

                boolean found = false;
                Building loadPaths = null;
                //TODO check se non trovato
                for(int i = 0; !found && i < response.length; ++i) {
                    if(response[i].name == name.toString()) {
                        loadPaths = response[i];
                        found = true;
                    }
                }

                String[] paths = new String[loadPaths.pathsInfos.size()];

                for(int i = 0; i < loadPaths.pathsInfos.size(); ++i) {
                    paths[i] = loadPaths.pathsInfos.get(i).title;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_path, R.id.pathName, paths);
                pathsResult.setAdapter(arrayAdapter);

                //TODO aggiungere numero percorsi
                // ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.row_building, R.id.textView6, array);
                //listView.setAdapter(arrayAdapter2);
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
                String buildingName = (pathsResult.getItemAtPosition(position)).toString();
                Intent i = new Intent(getApplicationContext(), PathActivity.class);
                i.putExtra("pathName", buildingName);
                startActivity(i);
            }
        });
    }
}
