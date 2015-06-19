package pt.isel.pdm.xsyncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class XSyncService extends Service {

    private XSyncAdapter mSyncAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        mSyncAdapter = new XSyncAdapter(this, true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapter.getSyncAdapterBinder();
    }
}
