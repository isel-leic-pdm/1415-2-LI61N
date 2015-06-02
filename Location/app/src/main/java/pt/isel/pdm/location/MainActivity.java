package pt.isel.pdm.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity  implements LocationListener {

    private Handler handler;

    private Runnable wifiUpdater = new Runnable() {
        @Override
        public void run() {
            WifiInfo info = wifiman.getConnectionInfo();
            String txt = "BSSID: ";
            if (info != null) {
                txt += info.getBSSID();
            }
            txtBSSID.setText(txt);
            handler.postDelayed(wifiUpdater, 5000);
        }
    };

    private Button butStart;
    private Button butStop;

    private TextView txtBSSID;
    private TextView txtProviders;
    private TextView txtMessage;

    private LocationManager locman;
    private WifiManager wifiman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler      = new Handler();

        butStart     = (Button)   findViewById(R.id.butStart);
        butStop      = (Button)   findViewById(R.id.butStop);

        txtBSSID     = (TextView) findViewById(R.id.txtBSSID);
        txtProviders = (TextView) findViewById(R.id.txtProviders);
        txtMessage   = (TextView) findViewById(R.id.txtMessage);

        locman       = (LocationManager) getSystemService(LOCATION_SERVICE);
        wifiman      = (WifiManager)     getSystemService(WIFI_SERVICE);

        handler.postDelayed(wifiUpdater, 5000);
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
        String providers = "Providers:   ";
        for (String provider : locman.getProviders(true)) {
            providers += provider + "   ";
            Log.i("pdm/location", "Using provider: " + provider);
            locman.requestLocationUpdates(provider, 5000, 0, this);
        }
        txtProviders.setText(providers);
    }

    public void onStopLocating(View view) {
        locman.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String provider  = location.getProvider();
        long   timestamp = location.getTime();
        double latitude  = location.getLatitude();
        double longitude = location.getLongitude();
        float  accuracy  = location.getAccuracy();

        String message = provider +
                "\n    Timestamp: " + timestamp +
                "\n    Accuracy: " + accuracy +
                "\n    Latitude: " + latitude +
                "\n    Longitude: " + longitude +
                "\n------------\n";

        txtMessage.setText(message + txtMessage.getText());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
