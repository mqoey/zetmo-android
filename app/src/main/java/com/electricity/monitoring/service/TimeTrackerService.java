package com.electricity.monitoring.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimeTrackerService extends Service {
    public TimeTrackerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}