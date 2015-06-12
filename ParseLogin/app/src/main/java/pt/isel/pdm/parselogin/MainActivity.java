package pt.isel.pdm.parselogin;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends ActionBarActivity {

    private EditText edtUsername;
    private EditText edtPassword;

    private TextView txtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        txtToken    = (TextView)findViewById(R.id.txtToken);
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

    public void onSignUp(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString();

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(ParseKeys.TEST_EMAIL_ID + username + ParseKeys.TEST_EMAIL_DOM);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "SIGNED UP", Toast.LENGTH_SHORT).show();
                    String token = ParseUser.getCurrentUser().getSessionToken();
                    txtToken.setText(token);
                } else {
                    Log.e("pdm/parselogin", "SIGN UP FAILED", e);
                    Toast.makeText(MainActivity.this, "SIGN UP FAILED", Toast.LENGTH_SHORT).show();
                    txtToken.setText("(no token)");
                    edtUsername.requestFocus();
                }
            }
        });
    }

    public void onSignIn(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "SIGNED IN", Toast.LENGTH_SHORT).show();
                    String token = ParseUser.getCurrentUser().getSessionToken();
                    txtToken.setText(token);
                } else {
                    Log.e("pdm/parselogin", "SIGN IN FAILED", e);
                    Toast.makeText(MainActivity.this, "SIGN IN FAILED", Toast.LENGTH_SHORT).show();
                    txtToken.setText("(no token)");
                    edtUsername.requestFocus();
                }
            }
        });
    }
}
