package pt.isel.pdm.nullauthuse;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String NULL_ACCOUNT_TYPE = "pt.isel.pdm.nullauth";
    private static final String NULL_GENERAL_SCOPE = "general";
    private static final String NULL_CLEANUP_SCOPE = "cleanup";

    private TextView txtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtToken = (TextView)findViewById(R.id.txtToken);
        if (savedInstanceState != null && savedInstanceState.containsKey(AccountManager.KEY_AUTHTOKEN)) {
            txtToken.setText(savedInstanceState.getString(AccountManager.KEY_AUTHTOKEN, ""));
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String lastToken = txtToken.getText().toString();
        if (!TextUtils.isEmpty(lastToken)) {
            outState.putString(AccountManager.KEY_AUTHTOKEN, lastToken);
        }
    }

    public void onGetAuth(View view) {
        AccountManager accman = AccountManager.get(this);
        accman.getAuthTokenByFeatures(NULL_ACCOUNT_TYPE, NULL_GENERAL_SCOPE, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Log.i("pdm/nullauthuse", "getAuthToken response arrived");

                        if (future.isCancelled()) {
                            Log.i("pdm/nullauthuse", "getAuthToken cancelled");
                            Toast.makeText(MainActivity.this, "CANCELLED", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Bundle data = null;
                        try {
                            data = future.getResult();
                        } catch (Exception e) {
                            Log.e("pdm/nullauthuse", "getAuthToken exception", e);
                        }

                        String authToken = data.getString(AccountManager.KEY_AUTHTOKEN);
                        Log.i("pdm/nullauthuse", "authToken = " + authToken);

                        txtToken.setText(authToken);
                    }
                }, null);
    }

    public void onInvalidate(View view) {
        String token = txtToken.getText().toString();
        if (!TextUtils.isEmpty(token)) {
            Log.i("pdm/nullauthuse", "invalidating token \"" + token + "\"");
            AccountManager accman = AccountManager.get(this);
            accman.invalidateAuthToken(NULL_ACCOUNT_TYPE, token);
            txtToken.setText("");
        }
    }

    public void onCleanup(View view) {
        AccountManager accman = AccountManager.get(this);
        accman.getAuthTokenByFeatures(NULL_ACCOUNT_TYPE, NULL_CLEANUP_SCOPE, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Log.i("pdm/nullauthuse", "cleanup response arrived");

                        Bundle data = null;
                        try {
                            data = future.getResult();
                        } catch (Exception e) {
                            Log.e("pdm/nullauthuse", "cleanup exception", e);
                        }

                        txtToken.setText("");
                    }
                }, null);
    }
}
