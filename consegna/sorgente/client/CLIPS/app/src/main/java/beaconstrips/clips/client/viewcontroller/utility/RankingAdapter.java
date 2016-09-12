package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Score;

/**
 * @file ResultAdapter.java
 * @date 2016-09-06
 * @version 1.20
 * @author Viviana Alessio
 *
 * Classe che permette di avere un Adapter per poter visualizzare correttamente gli elementi della ListView presente in activity_ranking.xml
 */

public class RankingAdapter extends BaseAdapter {

    private ArrayList<Score> listData;
    private LayoutInflater layoutInflater;

    public RankingAdapter(Context aContext, ArrayList<Score> listData) {
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

        String pos = String.valueOf(listData.get(position).position);
        String scor = String.valueOf(listData.get(position).score);
        String username = String.valueOf(listData.get(position).username);

        holder.position.setText(pos);
        holder.name.setText(username);
        holder.score.setText(scor);

        convertView.setTag(holder);

        return convertView;
    }

    static class ViewHolder {
        TextView position;
        TextView name;
        TextView score;
    }
}