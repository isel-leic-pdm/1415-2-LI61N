package pt.isel.pdm.parse01;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends ActionBarActivity {

    private EditText edtText;
    private ParseObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtText = (EditText) findViewById(R.id.edtWord);

        onRefresh(null);
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

    public void onSave(View view) {
        if (obj == null) {
            obj = new ParseObject("TagLine");
        }

        String tag = edtText.getText().toString();

        obj.put("tag", tag);
        obj.saveInBackground();
    }

    public void onRefresh(View view) {
        ParseQuery<ParseObject> objQuery =
            ParseQuery.getQuery("TagLine");

        objQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e != null) {
                    Log.d("pdm/parse", "parse.com: getFirst failed");
                } else {
                    obj = object;
                    edtText.setText(obj.getString("tag"));
                }
            }
        });
    }
}
