package com.lotusbin.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {

    public static final String CHANNEL_ID= "display_notificaiton";
    public static final int NOTIFICATION_ID =12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void displayNotification(View view) {

        if (createNotificationChannel()) {
           // return;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Notificaiton title");
        builder.setContentText("THis is the 1st notification example I am doing");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(NOTIFICATION_ID , builder.build());
    }

    private boolean createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "My personal notification to be displayed";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel nc = new NotificationChannel(CHANNEL_ID, name, importance);

                    nc.setDescription(description);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.createNotificationChannel(nc);
            return true;
        }
        return false;
    }
}
