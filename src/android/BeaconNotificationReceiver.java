package com.unarin.cordova.beacon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tlantic.customer.tlantic.MainActivity;
import com.tlantic.customer.tlantic.R;


public class BeaconNotificationReceiver extends BroadcastReceiver {

    /**
     * we use this single id so we can always remove the notification if desired
     */
    public static final int SINGLE_NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {

        // see link for details, this way we can intercept the notification in certain situations
        // http://commonsware.com/blog/2010/08/11/activity-notification-ordered-broadcast.html
        Log.d("CustomerTlantic", "Beacon received");

        // settings
        String ticker = "Tlantic Customer";
        String title = "Bem-vindo a Tlantic Store";
        String text = "Veja os seus cupões!";

        // raise the notification
        Intent targetIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        notification.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }

}
