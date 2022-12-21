package ru.shanin.workwithservice.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import ru.shanin.workwithservice.R;

public class ThreadForeground extends Thread {
    private final String LOG_TAG = ThreadForeground.class.getSimpleName();
    private static ThreadForeground instance;
    private boolean running;
    private MediaPlayer mediaPlayer;

    public static ThreadForeground getInstance(Context context) {
        if (instance == null)
            instance = new ThreadForeground(context);
        return instance;
    }

    private ThreadForeground(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.zayac);
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        showLog("run");
        mediaPlayer.start();
//        int i = 0;
//        while (running) {
//            try {
//                Thread.sleep(1000);
//            } catch (Exception ignored) {
//            }
//            showLog("Timer " + ((i++) + 1));
//        }
    }

    @Override
    public synchronized void start() {
        showLog("start");
        running = true;
        super.start();
    }

    @Override
    public void interrupt() {
        showLog("interrupt");
        mediaPlayer.stop();
        running = false;
    }

    private void showLog(String message) {
        Log.d(LOG_TAG, LOG_TAG + ": " + message);
    }
}
