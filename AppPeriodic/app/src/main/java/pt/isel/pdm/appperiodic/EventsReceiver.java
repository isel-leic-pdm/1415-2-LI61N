package pt.isel.pdm.appperiodic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import java.text.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventsReceiver extends BroadcastReceiver {

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private static int id = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AppPeriodic/Rcv", "onReceive");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i("AppPeriodic/Rcv", "onBoot");
        } else {
            Log.i("AppPeriodic/Rcv", "onAlarm");

            String time = TIME_FORMAT.format(new Date());

            Intent notifIntent = new Intent(context, MainActivity.class);

            PendingIntent pendNotifIntent =
                    PendingIntent.getActivity(context, 0, notifIntent, 0);

            Notification notif = new NotificationCompat.Builder(context)
                    .setContentTitle("PDM is great!")
                    .setContentText("[" + time + "] It's really great!")
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setAutoCancel(true)
                    .setContentIntent(pendNotifIntent)
                    .build();

            NotificationManager notifManager =
                    (NotificationManager)context
                            .getSystemService(context.NOTIFICATION_SERVICE);

            notifManager.notify(id, notif);
        }
    }
}
