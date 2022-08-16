package com.electricity.monitoring.tarrif;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.threshold.ThresholdActivity;

import java.util.ArrayList;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarrifActivity extends AppCompatActivity {

    TextView txtprice, txtdate;
    ProgressDialog loading;
    DBHandler dbHandler;
    ArrayList<Tarrif> tarrifArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarrif);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.tarrif);

//        sendNotification("Power cut notice", "They will be a power cut in your neighbourhood from 16:30 to 20:00");

        txtprice = findViewById(R.id.txt_price);
        txtdate = findViewById(R.id.txt_date);

        dbHandler = new DBHandler(TarrifActivity.this);
        fetchTarrifs();
    }

    public void fetchTarrifs() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Tarrif> call = apiInterface.getTarrif();
        call.enqueue(new Callback<Tarrif>() {
            @Override
            public void onResponse(@NonNull Call<Tarrif> call, @NonNull Response<Tarrif> response) {
                loading.dismiss();
                if (response.code() == 200) {

                    String tarrifID = response.body().getId();
                    String price = response.body().getPrice();
                    String date = response.body().getDate();

                    txtdate.setText(date);
                    txtprice.setText("$" + price + " /1KWh");

                    dbHandler.fetchTarrifs(date, price, tarrifID);
                }
            }

            @Override
            public void onFailure(Call<Tarrif> call, Throwable t) {
                loading.dismiss();
                tarrifArrayList = dbHandler.getTarrif();

                String price = tarrifArrayList.get(0).getPrice();
                String date = tarrifArrayList.get(0).getDate();

                txtdate.setText(date);
                txtprice.setText("$" + price + " /1KWh");
                Toasty.error(TarrifActivity.this, "No Internet Connectivity", Toasty.LENGTH_LONG).show();
            }
        });
    }

    public void sendNotification(String messageTitle, String messageBody) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ThresholdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel("my_notification", "n_channel", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.loading))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}