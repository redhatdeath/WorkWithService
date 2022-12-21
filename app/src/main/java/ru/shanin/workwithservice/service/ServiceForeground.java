package ru.shanin.workwithservice.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import ru.shanin.workwithservice.R;

public class ServiceForeground extends Service {
    private final String LOG_TAG = ServiceForeground.class.getSimpleName();
    private ThreadForeground threadForeground;

    private final String CHANNEL_ID = "channel_id";
    private final String CHANNEL_NAME = "channel_name";
    private final int NOTIFICATION_ID = 150;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Constructor
    public static Intent startService(Context context) {
        Intent intent = new Intent(context, ServiceForeground.class);
        // intent.putExtra("","");
        return intent;
    }

    // LifeCircle
    @Override
    public void onCreate() {
        showLog("onCreate");
        showNotification();
        setupThreadSimple(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showLog("onStartCommand");
        startThreadSimple();
        //stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        showLog("onDestroy");
        stopThreadSimple();
        super.onDestroy();
    }

    private void showLog(String message) {
        Log.d(LOG_TAG, LOG_TAG + ": " + message);
    }

    private void setupThreadSimple(Context context) {
        threadForeground = ThreadForeground.getInstance(context);
    }

    private void startThreadSimple() {
        threadForeground.start();
    }

    private void stopThreadSimple() {
        threadForeground.interrupt();
        try {
            threadForeground.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Notification createNotification() {
        return new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.mk0)
                .setContentTitle(getResources().getString(R.string.app_name) + " " + LOG_TAG)
                .setContentText("Notification text")
                .build();
    }

    private void createNotificationChannel() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private void showNotification() {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
    }
}
