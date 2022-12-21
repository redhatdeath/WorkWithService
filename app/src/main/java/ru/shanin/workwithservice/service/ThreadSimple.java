package ru.shanin.workwithservice.service;

import android.util.Log;

public class ThreadSimple extends Thread {
    private final String LOG_TAG = ThreadSimple.class.getSimpleName();
    private static ThreadSimple instance;
    private boolean running;

    public static ThreadSimple getInstance() {
        if (instance == null)
            instance = new ThreadSimple();
        return instance;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        showLog("run");
        int i = 0;
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
            showLog("Timer " + ((i++) + 1));
        }
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
        running = false;
    }

    private void showLog(String message) {
        Log.d(LOG_TAG, LOG_TAG + ": " + message);
    }
}
