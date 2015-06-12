package pt.isel.pdm.parselogin;

import android.app.Application;

import com.parse.Parse;

public class ParseLoginApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, ParseKeys.PARSE_APP_ID, ParseKeys.PARSE_CLIENT_ID);
    }
}
