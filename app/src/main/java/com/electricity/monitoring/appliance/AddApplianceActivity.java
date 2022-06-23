package com.electricity.monitoring.appliance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class AddApplianceActivity extends BaseActivity {

    ProgressDialog loading;

    EditText etxtApplianceName, etxtApplianceYears, etxtApplianceDescription, etxtApplianceConsumption, etxtApplianceCondition;
    TextView txtAddAppliance;
    ImageView imgAppliance;
    DBHandler dbHandler;
    String mediaPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appliance);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.add_appliance);

        etxtApplianceName = findViewById(R.id.etxt_appliance_name);
        etxtApplianceYears = findViewById(R.id.etxt_appliance_years);
        etxtApplianceDescription = findViewById(R.id.etxt_appliance_description);
        etxtApplianceConsumption = findViewById(R.id.etxt_appliance_consumption);
        etxtApplianceCondition = findViewById(R.id.etxt_appliance_condition);
        imgAppliance = findViewById(R.id.image_appliance);
        txtAddAppliance = findViewById(R.id.txt_add_appliance);

        dbHandler = new DBHandler(AddApplianceActivity.this);

        txtAddAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxtApplianceName.getText().toString().trim();
                String years = etxtApplianceYears.getText().toString().trim();
                String description = etxtApplianceDescription.getText().toString().trim();
                String consumption = etxtApplianceConsumption.getText().toString().trim();
                String condition = etxtApplianceCondition.getText().toString().trim();

                if (name.isEmpty()) {
                    etxtApplianceName.setError(getString(R.string.please_enter_appliance_name));
                    etxtApplianceName.requestFocus();
                } else if (description.isEmpty()) {
                    etxtApplianceDescription.setError(getString(R.string.please_enter_appliance_description));
                    etxtApplianceDescription.requestFocus();
                } else if (years.isEmpty()) {
                    etxtApplianceYears.setError(getString(R.string.please_enter_appliance_years));
                    etxtApplianceYears.requestFocus();
                } else if (consumption.isEmpty()) {
                    etxtApplianceConsumption.setError(getString(R.string.please_enter_appliance_consumption));
                    etxtApplianceConsumption.requestFocus();
                } else if (condition.isEmpty()) {
                    etxtApplianceCondition.setError(getString(R.string.please_enter_appliance_condition));
                    etxtApplianceCondition.requestFocus();
                } else {
                    dbHandler.addAppliance(name, description, condition, years, consumption, mediaPath);
                    Toasty.success(AddApplianceActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddApplianceActivity.this, ApplianceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        imgAppliance.setOnClickListener(v -> {

            Intent intent = new Intent(AddApplianceActivity.this, ImageSelectActivity.class);
            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
            startActivityForResult(intent, 1213);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {
                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                imgAppliance.setImageBitmap(selectedImage);
            }
        } catch (Exception e) {
            Toasty.error(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
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