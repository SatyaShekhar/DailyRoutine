package com.lotusbin.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.lotusbin.myapplication.task.TabWithFrameActivity;

public class MyNotificatinReceiver extends BroadcastReceiver {

    @Override
   public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context, TabWithFrameActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 3456, new Intent[]{intent1}, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL").setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Hello").setSubText("THis is sub text").setAutoCancel(true);
        notificationManager.notify(3456, builder.build());


    }
}
