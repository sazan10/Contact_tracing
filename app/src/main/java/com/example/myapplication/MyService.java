package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final int NOTIF_ID = 1338;
    private static final String NOTIF_CHANNEL_ID = "Test";
    Handler handler = new Handler();
    private static final String TAG = "LOCATION";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
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
//        handler.post(runnableCode);
        return START_STICKY;
    }

//    private Runnable runnableCode = new Runnable() {
//        @Override
//        public void run() {
//            // Do something here on the main thread
//            Log.d("Repeatition", "Repeating task");
//            // Repeat this the same runnable code block again another 2 seconds
//            // 'this' is referencing the Runnable object
//
//
//            handler.postDelayed(this, 2000);
//        }
//    };

//    private void getLocation(){
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener((new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location= task.getResult();
//
//                if(location !=null )
//                {            Log.i("Repeatitiobngnn", "Repeating task");
//
//                    try {
//                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                        List<Address> addresses = geocoder.getFromLocation(
//                                location.getLatitude(), location.getLongitude(), 1
//                        );
//                        Log.i("location", "latitude: "+ String.valueOf(addresses.get(0).getLongitude())+"    latitude: "+ String.valueOf(addresses.get(0).getLatitude()));
//                    }
//                    catch(IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                    Log.i("empty", "location empty");
//            }
//        }));
//    }
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



    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

      @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}