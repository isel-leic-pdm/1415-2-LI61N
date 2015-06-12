package pt.isel.pdm.nullauthenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class NullAuthenticator extends AbstractAccountAuthenticator {

    public NullAuthenticator(Context context) {
        super(context);
        Log.i("pdm/nullauth", "authenticator ctor");
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        Log.i("pdm/nullauth", "authenticator editProperties");
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator addAccount");
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator confirmCredentials");
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.i("pdm/nullauth", "authenticator getAuthToken");
        return null;
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
}
