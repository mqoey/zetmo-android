package com.electricity.monitoring.notification;

import android.app.Activity;
import android.os.Bundle;

import com.electricity.monitoring.R;

public class NotificationReceiver extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_result);

    }
}
