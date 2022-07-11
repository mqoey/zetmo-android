package com.electricity.monitoring.appliance;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.electricity.monitoring.Constant;
import com.electricity.monitoring.HomeActivity;
import com.electricity.monitoring.R;
import com.electricity.monitoring.auth.LoginActivity;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.File;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ViewApplianceActivity extends AppCompatActivity {

    TextView viewName, viewDescription, viewCondition, viewConsumption, viewYear;
    ImageView viewImage;
    Button editAppliance, deleteAppliance;
    String applianceID;

    private ArrayList<Appliance> applianceData;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appliance);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.view_appliance);

        viewName = findViewById(R.id.txt_view_name);
        viewDescription = findViewById(R.id.txt_view_description);
        viewCondition = findViewById(R.id.txt_view_condition);
        viewConsumption = findViewById(R.id.txt_view_consumption);
        viewYear = findViewById(R.id.txt_view_years);
        viewImage = findViewById(R.id.view_image);
        editAppliance = findViewById(R.id.btn_edit_appliance);
        deleteAppliance = findViewById(R.id.btn_delete_appliance);

        dbHandler = new DBHandler(ViewApplianceActivity.this);

        applianceID = getIntent().getExtras().getString(Constant.APPLIANCE_ID);
        getAppliance(applianceID);

        deleteAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ViewApplianceActivity.this);
                dialogBuilder
                        .withTitle(getString(R.string.delete))
                        .withMessage(R.string.delete_appliance)
                        .withEffect(Slidetop)
                        .withDialogColor("#637ECF") //use color code for dialog
                        .withButton1Text(getString(R.string.yes))
                        .withButton2Text(getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dbHandler.deleteAppliance(applianceID);
                                Toasty.success(ViewApplianceActivity.this,"Deleted successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), ApplianceActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialogBuilder.dismiss();
                                finish();
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

        editAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewApplianceActivity.this, EditApplianceActivity.class);
                intent.putExtra(Constant.APPLIANCE_ID, applianceID);
                startActivity(intent);
            }
        });
    }

    public void getAppliance(String applianceID){
        applianceData = dbHandler.getAppliancesByID(applianceID);

        final String appliance_id = applianceData.get(0).getApplianceId();
        String name = applianceData.get(0).getApplianceName();
        String description = applianceData.get(0).getApplianceDescription();
        String years = applianceData.get(0).getApplianceYears();
        String condition = applianceData.get(0).getApplianceCondition();
        String consumption = applianceData.get(0).getApplianceConsumption();
        String image = applianceData.get(0).getApplianceImage();

        viewName.setText("Name : " + name);
        viewCondition.setText("Condition : " + condition);
        viewConsumption.setText("Consumption : "+ consumption + " Kw\\h");
        viewDescription.setText("Description : " + description);
        viewYear.setText("Years : " + years + " yrs");

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                viewImage.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(ViewApplianceActivity.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(viewImage);
            }
        }
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