/**
 * @file BuildingAdapter.java
 * @date 2016-08-19
 * @version 1.0.0
 * @author Matteo Franco
 *
 * Classe che permette di avere un Adapter per poter visualizzare correttamente gli elementi della ListView presente in activity_building.xml
 */

package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.PathInfo;

public class BuildingAdapter extends BaseAdapter {

   private static ArrayList<PathInfo> searchArrayList;

   private LayoutInflater mInflater;

   public BuildingAdapter(Context context, ArrayList<PathInfo> results) {
      searchArrayList = results;
      mInflater = LayoutInflater.from(context);
   }

   public int getCount() {
      return searchArrayList.size();
   }

   public Object getItem(int position) {
      return searchArrayList.get(position);
   }

   public long getItemId(int position) {
      return position;
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (convertView == null) {
         convertView = mInflater.inflate(R.layout.row_path, null);
         holder = new ViewHolder();
         holder.pathId = (TextView) convertView.findViewById(R.id.pathId);
         holder.pathName = (TextView) convertView.findViewById(R.id.pathName);
         convertView.setTag(holder);
      }

      else {
         holder = (ViewHolder) convertView.getTag();
      }

      holder.pathId.setText(String.valueOf(searchArrayList.get(position).id));
      holder.pathName.setText(searchArrayList.get(position).title);
      Log.i("Id", String.valueOf(searchArrayList.get(position).id));
      Log.i("Name", searchArrayList.get(position).title);

      return convertView;
   }

   static class ViewHolder {
      TextView pathId;
      TextView pathName;
   }
}
