package clips.matteo.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Intent i = getIntent();
            String valueName = i.getStringExtra("buildingName");
            TextView name = (TextView) findViewById(R.id.textView7);
            name.setText(valueName);
    }
}
