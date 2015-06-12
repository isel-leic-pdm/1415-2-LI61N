package pt.isel.pdm.nullauthenticator;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AccountAuthenticatorActivity {

    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("pdm/nullauth", "sign[up|in] activity created");
        setContentView(R.layout.activity_login);
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
    }

    public void doSignUpOrIn(View view) {
        Log.i("pdm/nullauth", "sign[up|in] activity doSignUp");
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString();
        if (validate(username, password)) {
            final ProgressDialog progDialog = ProgressDialog.show(this, "Null Sign Up", "Please wait...", true);
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... _) {
                    // Simulating remote account creation. Always succeeds.
                    try { Thread.sleep(3000); } catch (Exception e) {}
                    return NullAuthenticator.FAKE_AUTH_TOKEN + username;
                }

                @Override
                protected void onPostExecute(String authToken) {
                    progDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "SIGNED UP", Toast.LENGTH_SHORT).show();
                    registerAccount(username, password, authToken);
                }
            }.execute((Void) null);
        }
    }


    private boolean validate(String username, String password) {
        return validateUsername(username) && validatePassword(password);
    }

    private boolean validateUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
            edtUsername.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void registerAccount(String username, String password, String authToken) {
        Log.i("pdm/nullauth", "sign[up|in] activity registerAccount");
        String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        AccountManager accman = AccountManager.get(this);

        // Try to create a new local account.
        Account account = new Account(username, accountType);
        if (!accman.addAccountExplicitly(account, password, null)) {
            accman.setPassword(account, password);
        } else {
            Toast.makeText(LoginActivity.this, "NEW ACCOUNT", Toast.LENGTH_SHORT).show();
        }

        // Store the authentication token.
        accman.setAuthToken(account, NullAuthenticator.GENERAL_SCOPE, authToken);

        Bundle resultBundle = new Bundle();
        resultBundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        resultBundle.putString(AccountManager.KEY_ACCOUNT_NAME, username);
        resultBundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);

        Intent resultIntent = new Intent();
        resultIntent.putExtras(resultBundle);

        setAccountAuthenticatorResult(resultBundle);
        setResult(RESULT_OK, resultIntent);

        finish();
    }


}
