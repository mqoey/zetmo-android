package com.electricity.monitoring.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartOnBoot extends Service {
    public StartOnBoot() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate(){
        super.onCreate();
        checkTime();
    }

    public void checkTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        String time = simpleTimeFormat.format(calendar.getTime());
        String date = simpleDateFormat.format(calendar.getTime());

        System.out.println("time ----------------" + time + " --------------- " + date);
    }
}