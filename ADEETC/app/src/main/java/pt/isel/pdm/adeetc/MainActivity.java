package pt.isel.pdm.adeetc;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import pt.isel.pdm.adeetc.provider.ADEETCContract;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LECTURERS_LOADER = 0;

    private ListView listView;
    private SimpleCursorAdapter adapter;

    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[] {
                        ADEETCContract.Lecturers.NAME,
                        ADEETCContract.Lecturers.EMAIL
                },
                new int[] {
                        android.R.id.text1,
                        android.R.id.text2
                },
                0);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(LECTURERS_LOADER, null, this);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
                this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 20*1000, 20*1000,
                pi);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LECTURERS_LOADER:
                return new CursorLoader(
                        this,
                        ADEETCContract.Lecturers.CONTENT_URI,
                        ADEETCContract.Lecturers.PROJECTION_ALL,
                        null,
                        null,
                        null
                );
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void onTest1(View view) {
        TestProviderService.startActionTest1(this);
    }

    public void onTest2(View view) {

    }

    public void onClear(View view) {
        TestProviderService.startActionClear(this);
    }
}
