package com.electricity.monitoring.appliance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;

import java.io.File;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EditApplianceActivity extends AppCompatActivity {

    EditText etxtApplianceName, etxtApplianceYears, etxtApplianceDescription, etxtApplianceConsumption, etxtApplianceCondition;
    TextView txtEditAppliance;
    ImageView imgAppliance;
    DBHandler dbHandler;
    String mediaPath = "", applianceID;
    private ArrayList<Appliance> applianceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appliance);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.appliances_details);

        etxtApplianceName = findViewById(R.id.etxt_edit_appliance_name);
        etxtApplianceYears = findViewById(R.id.etxt_edit_appliance_years);
        etxtApplianceDescription = findViewById(R.id.etxt_edit_appliance_description);
        etxtApplianceConsumption = findViewById(R.id.etxt_edit_appliance_consumption);
        etxtApplianceCondition = findViewById(R.id.etxt_edit_appliance_condition);
        imgAppliance = findViewById(R.id.edit_image_appliance);
        txtEditAppliance = findViewById(R.id.txt_edit_appliance);

        dbHandler = new DBHandler(EditApplianceActivity.this);

        applianceID = getIntent().getExtras().getString(Constant.APPLIANCE_ID);
        getAppliance(applianceID);

        txtEditAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAppliance(applianceID);
            }
        });
    }

    public void getAppliance(String applianceID) {
        applianceData = dbHandler.getAppliancesByID(applianceID);

        final String appliance_id = applianceData.get(0).getApplianceId();
        String name = applianceData.get(0).getApplianceName();
        String description = applianceData.get(0).getApplianceDescription();
        String condition = applianceData.get(0).getApplianceCondition();
        String years = applianceData.get(0).getApplianceYears();
        String consumption = applianceData.get(0).getApplianceConsumption();
        String image = applianceData.get(0).getApplianceImage();

        etxtApplianceName.setText(name);
        etxtApplianceCondition.setText(condition);
        etxtApplianceConsumption.setText(consumption);
        etxtApplianceDescription.setText(description);
        etxtApplianceYears.setText(years);

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                imgAppliance.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(EditApplianceActivity.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(imgAppliance);
            }
        }
    }

    public void updateAppliance(String applianceID) {
        applianceData = dbHandler.getAppliancesByID(applianceID);

        final String appliance_id = applianceData.get(0).getApplianceId();
        String name = applianceData.get(0).getApplianceName();
        String description = applianceData.get(0).getApplianceDescription();
        String condition = applianceData.get(0).getApplianceCondition();
        String years = applianceData.get(0).getApplianceYears();
        String consumption = applianceData.get(0).getApplianceConsumption();
        String image = applianceData.get(0).getApplianceImage();

        Integer status = dbHandler.updateAppliance(appliance_id, name, description, condition, years, consumption, image);
        System.out.println("status ---------------------- " + status);
        if (status > 0){
        Toasty.success(EditApplianceActivity.this, "Edited successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditApplianceActivity.this, ApplianceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        }
        else {
            Toasty.error(EditApplianceActivity.this, "Failed updating", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditApplianceActivity.this, ApplianceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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