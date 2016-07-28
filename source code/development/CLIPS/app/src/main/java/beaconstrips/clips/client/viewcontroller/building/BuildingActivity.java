package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;

public class BuildingActivity extends AppCompatActivity {

    private ListView pathsResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        //Intent i = getIntent();
        //String valueName = i.getStringExtra("pathName");
        //TextView name = (TextView) findViewById(R.id.buildingName);
        //name.setText(valueName);
        pathsResult = (ListView) findViewById(R.id.pathsResult);
        setButtons();
        setItems();
        String[] array = {"Percorso1", "Percorso2", "Percorso3", "Percorso4", "Percorso5", "Percorso6", "Percorso7", "Percorso8"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.row_path, R.id.pathName, array);
        pathsResult.setAdapter(arrayAdapter);
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
