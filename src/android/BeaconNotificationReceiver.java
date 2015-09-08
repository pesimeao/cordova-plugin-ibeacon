package com.unarin.cordova.beacon;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class BeaconNotificationReceiver extends BroadcastReceiver {

    /**
     * we use this single id so we can always remove the notification if desired
     */
    public static final int SINGLE_NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {

        // see link for details, this way we can intercept the notification in certain situations
        // http://commonsware.com/blog/2010/08/11/activity-notification-ordered-broadcast.html
        Log.d("myapp", "entrei");

        // raise the notification
       /* NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("aaa")
                        .setContentText("asdasdas")
                        .setAutoCancel(true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(BeaconNotificationReceiver.SINGLE_NOTIFICATION_ID, builder.build());*/

        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
                .setTicker("Tlantic Customer")
                .setContentTitle("Bem-vindo a Tlantic Store")
                .setContentText("veja os seus cupões")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

}
