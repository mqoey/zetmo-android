package com.electricity.monitoring;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.electricity.monitoring.auth.LoginActivity;
import com.electricity.monitoring.database.DBHandler;

public class SplashActivity extends AppCompatActivity {

DBHandler dbHandler;
    public static int splashTimeOut = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbHandler = new DBHandler(SplashActivity.this);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(() -> {
            checkAuthStatus();
            finish();
        }, splashTimeOut);
    }

    public void checkAuthStatus()
    {
        boolean status = dbHandler.checkLogin();

        if (status)
        {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}