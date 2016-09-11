package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.ProofResult;

/**
 * @file ResultAdapter.java
 * @date 2016-08-19
 * @version 1.0.0
 * @author Viviana Alessio
 *
 * Classe che permette di avere un Adapter per poter visualizzare correttamente gli elementi della ListView presente in activity_saved_results.xml
 */
public class ResultsAdapter extends BaseAdapter {

    // risultatoProva è una classe di prova che modella il risultato di una prova. (contiene 4 stringhe e i loro relativi metodi set e get)
    private ArrayList<ProofResult> listData;
    private LayoutInflater layoutInflater;

    public ResultsAdapter(Context aContext, ArrayList<ProofResult> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_result, null);
            holder = new ViewHolder();
            holder.tappaView = (TextView) convertView.findViewById(R.id.nameStep);
            holder.durataView = (TextView) convertView.findViewById(R.id.totalPathTime);
            holder.punteggioView = (TextView) convertView.findViewById(R.id.totalScore);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        String duration = String.valueOf(listData.get(position).getDuration());
        String tappa = String.valueOf(listData.get(position).id);
        String score = String.valueOf(listData.get(position).score);
        holder.durataView.setText(duration);
        holder.tappaView.setText(tappa);
        holder.punteggioView.setText(score);
        return convertView;
    }

    static class ViewHolder {
        TextView tappaView;
        TextView durataView;
        TextView punteggioView;
    }
}
