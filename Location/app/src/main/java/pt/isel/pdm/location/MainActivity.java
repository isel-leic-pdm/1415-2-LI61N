package pt.isel.pdm.location;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Button butStart;
    private Button butStop;

    private TextView txtBSSID;
    private TextView txtProviders;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butStart     = (Button)   findViewById(R.id.butStart);
        butStop      = (Button)   findViewById(R.id.butStop);

        txtBSSID     = (TextView) findViewById(R.id.txtBSSID);
        txtProviders = (TextView) findViewById(R.id.txtProviders);
        txtMessage   = (TextView) findViewById(R.id.txtMessage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartLocating(View view) {

    }

    public void onStopLocating(View view) {
        
    }
}
