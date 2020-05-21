package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final int NOTIF_ID = 1338;
    private static final String NOTIF_CHANNEL_ID = "Test";
    Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        super.onStartCommand(intent, flags, startId);
//        if(intent.getAction()!=null)
//            Log.e("hello",intent.getAction().toString());

        if(intent.getAction()!=null && intent.getAction().equals("STOP")){
            stopSelf();

            return START_NOT_STICKY;
        }
        startForeground1();

// Define the code block to be executed

// Run the above code block on the main thread after 2 seconds
        handler.post(runnableCode);
        return START_STICKY;
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d("Repeatition", "Repeating task");
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 2000);
        }
    };

    private void startForeground1() {
        Log.i("notification", "working notification");
        Intent notificationIntent = new Intent(this, MyService.class);
//        stopSelf.setAction("STOP");
//    stopSelf.setAction(this.ACTION_STOP_SERVICE);
//        PendingIntent pStopSelf = PendingIntent.getService(this, 0, null,PendingIntent.FLAG_CANCEL_CURRENT);
        notificationIntent.setAction("STOP");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,channelId) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
//                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground,"STOP THE PROCESS",
                        pendingIntent)
                .build());

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }
}
