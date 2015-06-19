package pt.isel.pdm.nullauthenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class NullAuthenticator extends AbstractAccountAuthenticator {

    public static final String FAKE_AUTH_TOKEN = "fake.token.for.";
    public static final String NULL_GENERAL_SCOPE = "general";
    public static final String NULL_CLEANUP_SCOPE = "cleanup";

    private final Context mContext;
    private final AccountManager mAccMan;

    public NullAuthenticator(Context context) {
        super(context);
        Log.i("pdm/nullauth", "authenticator ctor");
        mContext = context;
        mAccMan = AccountManager.get(context);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        Log.i("pdm/nullauth", "authenticator editProperties");
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator addAccount");

        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator confirmCredentials");
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator getAuthToken");

        // :::: FOR DEMO PURPOSES ONLY. REMOVE FROM REAL IMPLEMENTATION. ::::
        if (authTokenType.equals(NULL_CLEANUP_SCOPE)) {
            Log.i("pdm/nullauth", "authenticator cleanup");
            String authToken = mAccMan.peekAuthToken(account, authTokenType);
            if (!TextUtils.isEmpty(authToken)) {
                Log.i("pdm/nullauth", "authenticator cleanup: invalidating authToken");
                mAccMan.invalidateAuthToken(account.type, authToken);
            }
            String password = mAccMan.getPassword(account);
            if (password != null) {
                Log.i("pdm/nullauth", "authenticator cleanup: clearing password");
                mAccMan.clearPassword(account);
            }
            Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "cleanup performed");
            return result;
        }
        // :::: ::::

        if (!authTokenType.equals(NULL_GENERAL_SCOPE)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        String authToken = mAccMan.peekAuthToken(account, authTokenType);
        Log.i("pdm/nullauth", "authenticator getAuthToken (local): " + authToken);

        if (TextUtils.isEmpty(authToken)) {
            final String password = mAccMan.getPassword(account);
            if (password != null) {
                Log.i("pdm/nullauth", "getAuthToken login");
                try {
                    authToken = performLogin(account.name, password);
                    if (!TextUtils.isEmpty(authToken)) {
                        mAccMan.setAuthToken(account, authTokenType, authToken);
                    }
                    Log.i("pdm/nullauth", "getAuthToken (remote): " + authToken);
                } catch (Exception e) {
                    Log.e("pdm/null.auth", "getAuthToken (remote) failed", e);
                    mAccMan.clearPassword(account);
                }
            }
        }

        if (!TextUtils.isEmpty(authToken)) {
            Bundle res = new Bundle();
            res.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            res.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            res.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return res;
        }

        Log.i("pdm/nullauth", "getAuthToken: none");

        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        Log.i("pdm/nullauth", "authenticator getAuthTokenLabel");
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator updateCredentials");
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator hasFeatures");
        return null;
    }

    private String performLogin(String username, String password) {
        return FAKE_AUTH_TOKEN + username;
    }
}
