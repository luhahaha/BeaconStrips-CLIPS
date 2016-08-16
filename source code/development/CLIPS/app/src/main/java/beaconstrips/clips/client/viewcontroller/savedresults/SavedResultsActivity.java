/**
 @file SavedResultsActivity.java
 @date 2016-07-20
 @version 1.10
 @author Matteo Franco
 @description gestisce l'activity della classifica
 **/

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;

public class SavedResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_results);

        ListView listView = (ListView)findViewById(R.id.resultRows);
        String [] array = {"Percorso1","Percorso2","Percorso3","Percorso4","Percorso5","Percorso6"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.row_result, R.id.namePathLabel, array);
        listView.setAdapter(arrayAdapter);
        /*TODO add cases if
            - user is not logged in
            - there are no results available
         */
    }
}
