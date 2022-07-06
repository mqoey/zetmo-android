package com.electricity.monitoring;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.electricity.monitoring.appliance.ApplianceActivity;
import com.electricity.monitoring.auth.LoginActivity;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.profile.ProfileActivity;
import com.electricity.monitoring.tarrif.TarrifActivity;
import com.electricity.monitoring.tokens.TokenActivity;
import com.electricity.monitoring.utils.BaseActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class HomeActivity extends BaseActivity {

    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long backPressed;
    CardView cardAppliances, cardProducts, cardProfile, cardTarrifs, cardTokens, cardReport, cardSettings, cardExpense, cardLogout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;

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

        dbHandler = new DBHandler(HomeActivity.this);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

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
}