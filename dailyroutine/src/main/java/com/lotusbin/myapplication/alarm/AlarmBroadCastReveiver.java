package com.lotusbin.myapplication.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.model.ScheduledTask;
import com.lotusbin.myapplication.task.TabWithFrameActivity;

public class AlarmBroadCastReveiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*MediaPlayer mediaPlayer = MediaPlayer.create (context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();*/

       ScheduledTask scheduledTask = (ScheduledTask) intent.getSerializableExtra("SCHEDULE_TASK");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context, TabWithFrameActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO Question: Can we send intent to the Notification channel
            CharSequence name = "Personal notification";
            String description = "Personal description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("123456", name, importance);
            notificationChannel.setDescription(description);
            //notificationChannel.setShowBadge(true);

            notificationManager.createNotificationChannel(notificationChannel);

        } else {
            PendingIntent pendingIntent = PendingIntent.getActivity(context,                100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Hello").setContentText("How R u")  .setAutoCancel(true);

            notificationManager.notify(100, builder.build());
        }

    }
}
