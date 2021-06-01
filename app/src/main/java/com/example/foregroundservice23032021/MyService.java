package com.example.foregroundservice23032021;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    String CHANNEL_ID = "CHANNEL_ID";
    int mCount = 1;
    NotificationManager mNotificationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BBB", "onCreate Service");
        startForeground(1 , createNotification(this,mCount,CHANNEL_ID));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNotificationManager.notify(1,createNotification(MyService.this,++mCount,CHANNEL_ID));
                new Handler().postDelayed(this,1000);
            }
        },1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BBB", "onDestroy Service");
    }

    public Notification createNotification(Context context, int count, String channelId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.drawable.ic_baseline_message_24);
        builder.setContentTitle("Hệ thống có thông báo mới");
        builder.setContentText("Có " +count + " tin nhắn mới" );
        builder.setShowWhen(true);
        builder.setPriority(Notification.PRIORITY_HIGH);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        return builder.build();
    }
}
