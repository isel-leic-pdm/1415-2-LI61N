package pt.isel.pdm.xsyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class XSyncApp extends Application {

    public static final String AUTHORITY = "pt.isel.pdm.xsync.provider";
    public static final String ACCOUNT_TYPE = "pt.isel.pdm.xsync";
    public static final String ACCOUNT = "xsync";

    private Account mAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        mAccount = createSyncAccount(this);
    }

    private Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager)context.getSystemService(ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.i("pdm/xsync", "account created");
            ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);
            ContentResolver.addPeriodicSync(newAccount, AUTHORITY, Bundle.EMPTY, 40);
        }
        return newAccount;
    }

    public Account getAccount() {
        return mAccount;
    }
}
