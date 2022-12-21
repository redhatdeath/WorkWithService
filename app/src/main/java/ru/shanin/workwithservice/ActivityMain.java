package ru.shanin.workwithservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ru.shanin.workwithservice.databinding.ActivityMainBinding;
import ru.shanin.workwithservice.service.ServiceForeground;
import ru.shanin.workwithservice.service.ServiceSimple;

public class ActivityMain extends AppCompatActivity {

    private final String LOG_TAG = ActivityMain.class.getSimpleName();
    private final String CHANNEL_ID = "channel_id";
    private final String CHANNEL_NAME = "channel_name";
    private final int NOTIFICATION_ID = 50;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btSimpleService.setOnClickListener(v -> {
            workWithSimpleService();
        });
        binding.btForegroundService.setOnClickListener(v -> {
            workWithForegroundService();
        });
        binding.btStopService.setOnClickListener(v -> {
            workWithStopService();
        });
        binding.btTest.setOnClickListener(v -> {
            workWithTest();
        });
    }

    private void workWithSimpleService() {
        showLog("Button \"" + binding.btSimpleService.getText().toString() + "\" was pressed.");
        startServiceSimple(getApplicationContext());
    }

    private void workWithForegroundService() {
        showLog("Button \"" + binding.btForegroundService.getText().toString() + "\" was pressed.");
        startServiceForeground(getApplicationContext());
    }

    private void workWithStopService() {
        showLog("Button \"" + binding.btStopService.getText().toString() + "\" was pressed.");
        stopAllService(getApplicationContext());
    }

    private void workWithTest() {
        showLog("Button \"" + binding.btTest.getText().toString() + "\" was pressed.");
    }

    private void showLog(String message) {
        Log.d(LOG_TAG, LOG_TAG + ": " + message);
    }

    private void startServiceSimple(Context context) {
        Intent intent = ServiceSimple.startService(context);
        startService(intent);
        showNotification();
    }

    private void startServiceForeground(Context context) {
        Intent intent = ServiceForeground.startService(context);
        startForegroundService(intent);
    }

    private void showNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificationManager.createNotificationChannel(notificationChannel);
        Notification notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.mk0)
                        .setContentTitle(getResources().getString(R.string.app_name) + " " + LOG_TAG)
                        .setContentText("Notification text")
                        .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void deleteNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void stopAllService(Context context) {
        stopService(ServiceSimple.startService(context));
        stopService(ServiceForeground.startService(context));
        deleteNotification();
    }
}