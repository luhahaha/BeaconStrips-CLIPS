/**
 @file SavedResultsActivity.java
 @date 2016-07-21
 @version 1.10
 @author Matteo Franco
 @description gestisce l'activity dei risultati salvati
 **/

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.utility.ResultsAdapter;
import beaconstrips.clips.client.viewcontroller.utility.risultatoProva;

public class SavedResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_results);


        ListView listView = (ListView)findViewById(R.id.resultRows);
        ArrayList lista = getListData();
        // se la lista è vuota visualizzo il messaggio "Nessun risultato da visualizzare"
        if(lista.size() == 0){
            TextView noResults = (TextView)findViewById(R.id.noResults);
            noResults.setVisibility(View.VISIBLE);
            noResults.setText("Nessun risultato da visualizzare");
        }
        else {
            listView.setAdapter(new ResultsAdapter(this, lista));
        }

        /*TODO add cases if
            - user is not logged in
         */
    }

    private ArrayList getListData() {
        // prova di esempio, poi questi dati verranno presi dal DB
        ArrayList<risultatoProva> results = new ArrayList<risultatoProva>();

        risultatoProva ris = new risultatoProva();
        ris.setData("10 Agosto");
        ris.setDurata("2 minuti");
        ris.setEdificio("Torre Archimede");
        ris.setPunteggio("20 punti");
        results.add(ris);

        // Add some more dummy data for testing
        return results;
    }
}
