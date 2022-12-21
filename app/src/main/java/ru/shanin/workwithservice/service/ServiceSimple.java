package ru.shanin.workwithservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServiceSimple extends Service {
    private final String LOG_TAG = ServiceSimple.class.getSimpleName();
    private ThreadSimple threadSimple;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Constructor
    public static Intent startService(Context context) {
        Intent intent = new Intent(context, ServiceSimple.class);
        // intent.putExtra("","");
        return intent;
    }

    // LifeCircle
    @Override
    public void onCreate() {
        showLog("onCreate");
        setupThreadSimple();
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

    private void setupThreadSimple() {
        threadSimple = ThreadSimple.getInstance();
    }

    private void startThreadSimple() {
        threadSimple.start();
    }

    private void stopThreadSimple() {
        threadSimple.interrupt();
        try {
            threadSimple.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
