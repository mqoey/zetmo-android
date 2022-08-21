package com.electricity.monitoring;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLembuit")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Threshold reminder")
                .setContentText("You have reached your usage limit")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());

        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();
    }
}
