package pt.isel.pdm.adeetc;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;

import pt.isel.pdm.adeetc.provider.ADEETCContract;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TestProviderService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TEST1 = "pt.isel.pdm.adeetc.action.TEST1";
    private static final String ACTION_CLEAR = "pt.isel.pdm.adeetc.action.CLEAR";

    // TODO: Rename parameters
    // private static final String EXTRA_PARAM1 = "pt.isel.pdm.adeetc.extra.PARAM1";
    // private static final String EXTRA_PARAM2 = "pt.isel.pdm.adeetc.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionTest1(Context context) {
        Intent intent = new Intent(context, TestProviderService.class);
        intent.setAction(ACTION_TEST1);
        context.startService(intent);
    }

    public static void startActionClear(Context context) {
        Intent intent = new Intent(context, TestProviderService.class);
        intent.setAction(ACTION_CLEAR);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestProviderService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    */

    public TestProviderService() {
        super("TestProviderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TEST1.equals(action)) {
                handleActionTest1();
            } else if (ACTION_CLEAR.equals(action)) {
                handleActionClear();
            }
        }
    }

    private void handleActionTest1() {
        ContentValues values;

        ContentResolver resolver = getContentResolver();

        values = createValues(1754, "João Trindade", "jtrindade@cc.isel.ipl.pt");
        resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);

        values = createValues(1234, "Carlos Guedes", "cguedes@cc.isel.ipl.pt");
        resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);

        values = createValues(1369, "Paulo Pereira", "palbp@cc.isel.ipl.pt");
        resolver.insert(ADEETCContract.Lecturers.CONTENT_URI, values);

    }

    private void handleActionClear() {
        getContentResolver().delete(ADEETCContract.Lecturers.CONTENT_URI, null, null);
    }

    ContentValues createValues(int code, String name, String email) {
        ContentValues values = new ContentValues();
        values.put(ADEETCContract.Lecturers.CODE, code);
        values.put(ADEETCContract.Lecturers.NAME, name);
        values.put(ADEETCContract.Lecturers.EMAIL, email);
        return values;
    }
}
