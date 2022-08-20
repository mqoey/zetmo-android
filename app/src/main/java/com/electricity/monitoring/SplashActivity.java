package com.electricity.monitoring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.auth.LoginActivity;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.tarrif.TarrifActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    public static int splashTimeOut = 3000;
    DBHandler dbHandler;
    TarrifActivity tarrifActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Tarrif> call = apiInterface.getTarrif();
        call.enqueue(new Callback<Tarrif>() {
            @Override
            public void onResponse(@NonNull Call<Tarrif> call, @NonNull Response<Tarrif> response) {
                if (response.code() == 200) {
                    String tarrifID = response.body().getId();
                    String price = response.body().getPrice();
                    String date = response.body().getDate();
                    dbHandler.fetchTarrifs(date, price, tarrifID);
                }
            }

            @Override
            public void onFailure(Call<Tarrif> call, Throwable t) {
                Toasty.error(SplashActivity.this, "No Internet Connectivity", Toasty.LENGTH_SHORT).show();
            }
        });

        dbHandler = new DBHandler(SplashActivity.this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        String today = sdf.format(calendar1.getTime());

//        dbHandler.updateifApplianceTimer(yesterday);
        dbHandler.updatedApplianceTimer(yesterday, today);
        dbHandler.updateApplianceTimer(yesterday);

//        if (dbHandler.checkThreshold().equals("no threshold")){
//            dbHandler.addThreshold("0");
//        }

        dbHandler.checkThreshold();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(() -> {
            checkAuthStatus();
            finish();
        }, splashTimeOut);
    }

    public void checkAuthStatus() {
        boolean status = dbHandler.checkLogin();

        if (status) {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}