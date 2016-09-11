package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.ProofResult;

/**
 * @file ResultAdapter.java
 * @date 2016-09-6
 * @version 1.0.0
 * @author Viviana Alessio
 *
 * Classe che permette di avere un Adapter per poter visualizzare correttamente gli elementi della ListView presente in activity_saved_results.xml
 */
public class SavedResultsAdapter extends BaseAdapter {

    // risultatoProva è una classe di prova che modella il risultato di una prova. (contiene 4 stringhe e i loro relativi metodi set e get)
    private ArrayList<PathResult> listData;
    private LayoutInflater layoutInflater;

    public SavedResultsAdapter(Context aContext, ArrayList<PathResult> listData) {
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
            convertView = layoutInflater.inflate(R.layout.row_saved_results, null);
            holder = new ViewHolder();
            holder.buildingName = (TextView) convertView.findViewById(R.id.buildingName);
            holder.pathName = (TextView) convertView.findViewById(R.id.pathName);
            holder.score = (TextView) convertView.findViewById(R.id.totalScore);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        String score = String.valueOf(listData.get(position).totalScore);
        holder.buildingName.setText(listData.get(position).buildingName);
        holder.pathName.setText(listData.get(position).pathName);
        holder.score.setText(score);
        return convertView;
    }

    static class ViewHolder {
        TextView buildingName;
        TextView pathName;
        TextView score;
    }
}
