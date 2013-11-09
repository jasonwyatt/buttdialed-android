package us.jwf.buttdial.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;
import us.jwf.buttdial.models.Call;
import us.jwf.buttdial.utils.Logger;

public class MainActivity extends Activity {
    App app;
    ListView callLog;
    private CallLogAdapter callLogAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("MainActivity onCreate()");
        app = (App) getApplicationContext();

        setContentView(R.layout.main_activity);

        callLog = (ListView) findViewById(R.id.recent_calls_list);
        callLogAdapter = new CallLogAdapter(this);
        callLog.setAdapter(callLogAdapter);

        callLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Call clickedCall = callLogAdapter.getItem(position);
                app.sms().sendApologySMS(MainActivity.this, clickedCall.number, clickedCall.name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        callLogAdapter.refresh(true);
        if (callLogAdapter.getCount() > 0) {
            findViewById(R.id.no_recent_calls).setVisibility(View.GONE);
        } else {
            findViewById(R.id.no_recent_calls).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        final Activity self = this;

        menu.findItem(R.id.about_item).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                app.views().startActivity(self, AboutActivity.class);
                return true;
            }
        });
        menu.findItem(R.id.settings_item).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                app.views().startActivity(self, SettingsActivity.class);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
