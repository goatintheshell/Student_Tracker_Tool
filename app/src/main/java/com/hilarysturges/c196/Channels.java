package com.hilarysturges.c196;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Channels extends Application {
    public static final String CHANNEL_1_ID = "courseStartChannel";
    public static final String CHANNEL_2_ID = "courseEndChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"courseStartChannel", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This notifies the user if a course has started");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID,"courseEndChannel", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("This notifies the user if a course has ended");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager!=null) {
                manager.createNotificationChannel(channel1);
                manager.createNotificationChannel(channel2);
            }
        }
    }
}

