package clips.matteo.authentication;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
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

//TODO rendere scrollabile la list view

public class BuildingSearch extends AppCompatActivity {

    TextView distanceText;
    SeekBar distance;
    CheckBox searchRadius;
    Button showResult;
    ListView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_search);

        distanceText = (TextView) findViewById(R.id.textView3);
        distance = (SeekBar) findViewById(R.id.seekBar);
        searchRadius = (CheckBox) findViewById(R.id.checkRadius);
        showResult = (Button) findViewById(R.id.search_buldings);
        results = (ListView) findViewById(R.id.listView);


        distance.setEnabled(false);
        distanceText.setText(String.valueOf(distance.getProgress()));
        distanceText.setVisibility(View.INVISIBLE);
        results.setVisibility(View.INVISIBLE);


        ListView listView = (ListView)findViewById(R.id.listView);
        String [] array = {"Edificio1","Edificio2","Edificio3","Edificio4", "Edificio5", "Edificio6", "Edificio7", "Edificio8"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.building_row, R.id.textView4, array);
        listView.setAdapter(arrayAdapter);
        //ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.building_row, R.id.textView6, array);
        //listView.setAdapter(arrayAdapter2);

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
                Log.i("Clicked","Check");
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

    void setButton() {
        showResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                results.setVisibility(View.VISIBLE);
            }
        });
    }

    void setItems() {
        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String buildingName = (listView.getItemAtPosition(position)).toString();
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("buildingName", buildingName);
                startActivity(i);

            }
        });
    }
}