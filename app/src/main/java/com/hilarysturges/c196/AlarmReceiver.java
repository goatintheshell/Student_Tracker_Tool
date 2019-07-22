package com.hilarysturges.c196;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import java.util.Objects;

import static com.hilarysturges.c196.Channels.CHANNEL_1_ID;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("alarm request received");

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
//        SharedPreferences.Editor sharedPrefEditor = prefs.edit();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("default",
//                    "Start Notification",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("Start Notification");
//            if (nm != null) {
//                nm.createNotificationChannel(channel);
//            }
//        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, CHANNEL_1_ID);
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("WGU Alert")
                .setContentTitle("WGU Student Tracker Tool")
                .setContentText("Course Starting!")
                .setContentIntent(pendingI)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        if (nm != null) {
            nm.notify(1, b.build());
            Calendar nextNotifyTime = Calendar.getInstance();
            nextNotifyTime.add(Calendar.MINUTE, 5);
//            sharedPrefEditor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
//            sharedPrefEditor.apply();
        }
    }
}
