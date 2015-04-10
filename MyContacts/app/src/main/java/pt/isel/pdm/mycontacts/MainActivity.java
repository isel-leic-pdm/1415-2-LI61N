package pt.isel.pdm.mycontacts;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity
        extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CONTACTS_LOADER = 0;

    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);

        String[] from = new String[] { ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.LOOKUP_KEY };
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, from, to, 0);
        listView.setAdapter(adapter);

        LoaderManager lm = getLoaderManager();
        lm.initLoader(CONTACTS_LOADER, null, this);
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
            case CONTACTS_LOADER:
                return new CursorLoader(
                        this,
                        ContactsContract.Contacts.CONTENT_URI,
                        new String[] {
                                ContactsContract.Contacts._ID,
                                ContactsContract.Contacts.DISPLAY_NAME,
                                ContactsContract.Contacts.LOOKUP_KEY
                        },
                        ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1",
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME + " ASC"
                );
            default:
                Log.w("MyContacts", "Unknown id for loader");
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }
}
