/**
 * @file ResultActivity.java
 * @date 2016-07-20
 * @version 1.00
 * @author Matteo Franco
 * Si occupa di gestire l'activity dei risultati conseguiti dall'utente
 */

package beaconstrips.clips.client.viewcontroller.savedresults;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.DataRequestMaker;
import beaconstrips.clips.client.urlrequest.ServerError;
import beaconstrips.clips.client.viewcontroller.utility.ResultsAdapter;
import beaconstrips.clips.client.viewcontroller.utility.risultatoProva;

public class ResultActivity extends AppCompatActivity {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //TODO add buttons linking

        i = getIntent();
        double totalScore = i.getDoubleExtra("totalScore", 0.0);
        ((TextView) findViewById(R.id.totalScore)).setText(String.valueOf(totalScore));
        /*
        DataRequestMaker.getResults(getApplicationContext(), new AbstractDataManagerListener<PathResult[]>() {
        @Override
        public void onResponse(PathResult[] response) {

        }

        @Override
        public void onError(ServerError error) {

        }
    });
    */

        ListView listView = (ListView)findViewById(R.id.stepsResults);
        ArrayList lista = getListData();
        justifyListViewHeightBasedOnChildren(listView, lista);
        listView.setAdapter(new ResultsAdapter(this, lista));

    }

    private ArrayList getListData() {
        // prova di esempio, poi questi dati verranno presi dal DB
        // è il ResultAdapter che mette gli elementi di row_result al posto giusto e dentro la list view
        // fare un for per scorrere tutte le tappe
        ArrayList<risultatoProva> results = new ArrayList<risultatoProva>();

        risultatoProva ris = new risultatoProva();
        ris.setData("10 Agosto");
        ris.setDurata("2 minuti");
        ris.setEdificio("Torre1");
        ris.setPunteggio("20");
        results.add(ris);

        risultatoProva ris2 = new risultatoProva();
        ris2.setData("10 Agosto");
        ris2.setDurata("3 minuti");
        ris2.setEdificio("Torre2");
        ris2.setPunteggio("10");
        results.add(ris2);

        // Add some more dummy data for testing
        return results;
    }


    // setta la dimensione della listView in base a quanti elementi ci sono nella lista
    public static void justifyListViewHeightBasedOnChildren (ListView listView, ArrayList lista) {
        int rows = lista.size();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = rows*250;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
