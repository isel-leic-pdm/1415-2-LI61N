package pt.isel.pdm.nullauthenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NullAuthService extends Service {

    private NullAuthenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("pdm/nullauth", "service onCreate");
        authenticator = new NullAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("pdm/nullauth", "service onBind");
        return authenticator.getIBinder();
    }
}
