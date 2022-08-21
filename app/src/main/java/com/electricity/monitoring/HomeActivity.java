package com.electricity.monitoring;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import com.electricity.monitoring.appliance.ApplianceActivity;
import com.electricity.monitoring.auth.LoginActivity;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.neighbourhood.NeighbourhoodStageActivity;
import com.electricity.monitoring.profile.ProfileActivity;
import com.electricity.monitoring.stage.LoadsheddingActivity;
import com.electricity.monitoring.tarrif.TarrifActivity;
import com.electricity.monitoring.threshold.ThresholdActivity;
import com.electricity.monitoring.tokens.TokenActivity;
import com.electricity.monitoring.usage.UsageActivity;
import com.electricity.monitoring.utils.BaseActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Date;

public class HomeActivity extends BaseActivity {

    CardView cardAppliances, cardUsage, cardProfile, cardTarrifs, cardTokens, cardThresholds, cardLogout, cardLoadShedding, cardStage;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    ProgressDialog loading;
    String alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setElevation(0);

        cardLogout = findViewById(R.id.card_logout);
        cardAppliances = findViewById(R.id.card_appliances);
        cardProfile = findViewById(R.id.card_profile);
        cardTarrifs = findViewById(R.id.card_tarrifs);
        cardTokens = findViewById(R.id.card_tokens);
        cardUsage = findViewById(R.id.card_usage);
        cardThresholds = findViewById(R.id.card_thresholds);
        cardLoadShedding = findViewById(R.id.card_loadshedding);
        cardStage = findViewById(R.id.card_stages);

        alarm = "m";

        dbHandler = new DBHandler(HomeActivity.this);

        String threshold = dbHandler.checkThreshold();
        alarm = dbHandler.checkAlarm();

        if(alarm.isEmpty() || alarm == null)
        {
            sendNotification("Remaining Threshold", "Your remaining power is : " + threshold + "KWh");
        }
        else {
        if (Double.parseDouble(threshold) < Double.parseDouble(alarm)) {
            Intent intent = new Intent(HomeActivity.this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    HomeActivity.this, 234324243, intent, PendingIntent.FLAG_IMMUTABLE
            );
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

            dbHandler.checkAlarm();

            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(HomeActivity.this);
            dialogBuilder
                    .withTitle("Threshold reminder")
                    .withMessage("This is a reminder that you are left with less than " + alarm + "KWh")
                    .withEffect(Slidetop)
                    .withDialogColor("#637ECF") //use color code for dialog
                    .withButton1Text("Dismiss")
                    .withButton2Text("Snooze")
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbHandler.updateAlarm();
                            dialogBuilder.dismiss();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                        }
                    })
                    .show();

            sendNotification("Threshold reminder", "This is a reminder that you are left with less than " + alarm + "KWh");
        } else {
            sendNotification("Remaining Threshold", "Your remaining power is : " + threshold + "KWh");
        }
        }

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UsageActivity.class);
                startActivity(intent);
            }
        });

        cardLoadShedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoadsheddingActivity.class);
                startActivity(intent);
            }
        });

        cardStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NeighbourhoodStageActivity.class);
                startActivity(intent);
            }
        });

        cardThresholds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ThresholdActivity.class);
                startActivity(intent);
            }
        });

        cardTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TokenActivity.class);
                startActivity(intent);
            }
        });

        cardTarrifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TarrifActivity.class);
                startActivity(intent);
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cardAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ApplianceActivity.class);
                startActivity(intent);
            }
        });

        cardAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ApplianceActivity.class);
                startActivity(intent);
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(HomeActivity.this);
                dialogBuilder
                        .withTitle(getString(R.string.logout))
                        .withMessage(R.string.want_to_logout_from_app)
                        .withEffect(Slidetop)
                        .withDialogColor("#637ECF") //use color code for dialog
                        .withButton1Text(getString(R.string.yes))
                        .withButton2Text(getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editor.putString(Constant.SP_METER_NUMBER, "");
                                editor.putString(Constant.SP_PASSWORD, "");
                                editor.apply();

                                dbHandler.logoutUser();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
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
}