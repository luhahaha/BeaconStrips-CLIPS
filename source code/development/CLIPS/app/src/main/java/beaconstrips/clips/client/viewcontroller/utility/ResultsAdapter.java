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
 * Created by vivi on 20/08/16.
 */
public class ResultsAdapter extends BaseAdapter {

    // risultatoProva è una classe di prova che modella il risultato di una prova. (contiene 4 stringhe e i loro relativi metodi set e get)
    private ArrayList<risultatoProva> listData;
    private LayoutInflater layoutInflater;

    public ResultsAdapter(Context aContext, ArrayList<risultatoProva> listData) {
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
            holder.edificioView = (TextView) convertView.findViewById(R.id.building);
            holder.durataView = (TextView) convertView.findViewById(R.id.totalPathTime);
            holder.punteggioView = (TextView) convertView.findViewById(R.id.totalScore);
            holder.dataView = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        // getDurata, getData, eccetera sono metodi della classe di prova risultatoProva
        holder.durataView.setText(listData.get(position).getDurata());
        holder.dataView.setText(listData.get(position).getData());
        holder.edificioView.setText(listData.get(position).getEdificio());
        holder.punteggioView.setText(listData.get(position).getPunteggio());
        return convertView;
    }

    static class ViewHolder {
        TextView edificioView;
        TextView dataView;
        TextView durataView;
        TextView punteggioView;
    }
}
