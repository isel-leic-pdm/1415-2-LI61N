package pt.isel.pdm.appperiodic;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class PeriodicApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("AppPeriodic/App", "onCreate");

        Intent alarmIntent = new Intent(this, EventsReceiver.class);

        if (PendingIntent.getBroadcast(
                this, 0, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) == null) {
            Log.i("AppPeriodic/App", "noAlarm");
            PendingIntent pendingAlarmIntent =
                    PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

            AlarmManager alarmManager =
                    (AlarmManager)getSystemService(ALARM_SERVICE);

            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 12*1000,
                    12*1000,
                    pendingAlarmIntent);
        }
    }
}
