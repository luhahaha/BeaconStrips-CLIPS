package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;

/**
 * @file ResultAdapter.java
 * @date 2016-09-06
 * @version 1.0.0
 * @author Viviana Alessio
 *
 * Classe che permette di avere un Adapter per poter visualizzare correttamente gli elementi della ListView presente in activity_ranking.xml
 */

public class RankingAdapter extends BaseAdapter {

    // risultatoProva è una classe di prova che modella il risultato di una prova. (contiene 4 stringhe e i loro relativi metodi set e get)
    private ArrayList<risultatoProva> listData;
    private LayoutInflater layoutInflater;

    public RankingAdapter(Context aContext, ArrayList<risultatoProva> listData) {
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
            convertView = layoutInflater.inflate(R.layout.row_ranking, null);
            holder = new ViewHolder();
            holder.position = (TextView) convertView.findViewById(R.id.rankingPosition);
            holder.name = (TextView) convertView.findViewById(R.id.playerName);
            holder.score = (TextView) convertView.findViewById(R.id.totalScore);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position.setText(listData.get(position).getDurata());
        holder.name.setText(listData.get(position).getEdificio());
        holder.score.setText(listData.get(position).getPunteggio());
        return convertView;
    }

    static class ViewHolder {
        TextView position;
        TextView name;
        TextView score;
    }
}