package com.example.ivaylogeorgiev.androidexercise.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.ivaylogeorgiev.androidexercise.R;

/**
 * Created by ivaylogeorgiev on 18/04/2019.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        //Automatically cancel a notification if user clicks on it in the notification panel.
        builder.setAutoCancel(true)

                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Daily training")
                .setContentText("It is time for daily training")
                .setContentInfo("Info")
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }
}
