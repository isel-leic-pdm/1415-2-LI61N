package pt.isel.pdm.nullauthenticator;

import android.accounts.AccountAuthenticatorActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


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
        finish();
    }
}
