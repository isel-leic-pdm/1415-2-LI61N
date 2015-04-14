package pt.isel.pdm.adeetc;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
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
        final ContentResolver resolver = getContentResolver();
        new Thread() {
            public void run() {
                ContentValues values;

                values = createValues(1754, "João Trindade", "jtrindade@cc.isel.ipl.pt");
                resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);

                values = createValues(1234, "Carlos Guedes", "cguedes@cc.isel.ipl.pt");
                resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);

                values = createValues(1369, "Paulo Pereira", "palbp@cc.isel.ipl.pt");
                resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);
            }
        }.start();
    }

    public void onTest2(View view) {

    }

    public void onClear(View view) {
        final ContentResolver resolver = getContentResolver();
        new Thread() {
            public void run() {
                resolver.delete(ADEETCContract.Lecturers.CONTENT_URI, null, null);
            }
        }.start();
    }

    ContentValues createValues(int code, String name, String email) {
        ContentValues values = new ContentValues();
        values.put(ADEETCContract.Lecturers.CODE, code);
        values.put(ADEETCContract.Lecturers.NAME, name);
        values.put(ADEETCContract.Lecturers.EMAIL, email);
        return values;
    }
}
