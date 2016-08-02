package com.viator42.erikanote.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduleService extends Service {
    private Timer timer = null;

    public ScheduleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("ErikaNote", "Service started");
        if(timer == null)
        {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.v("ErikaNote", "Ticking");
            }
        }, 5000, 5000);

        return super.onStartCommand(intent, flags, startId);
    }

}
