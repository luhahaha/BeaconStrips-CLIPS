package beaconstrips.clips.client.viewcontroller.building;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import beaconstrips.clips.R;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        Intent i = getIntent();
        String valueName = i.getStringExtra("pathName");
        TextView name = (TextView) findViewById(R.id.nameLabel);
        name.setText(valueName);
    }

}
