package com.electricity.monitoring.threshold;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;

import java.util.ArrayList;

public class CalculateThresholdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner paymentOption;
    TextView btnCalculate, lastCalculated, givenCalculated;
    EditText amount;
    DBHandler dbHandler;
    ArrayList<String> appliance;
    ProgressDialog loading;
    private String[] optionList;
    private ArrayList<Appliance> applianceData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_threshold);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Set Alarm");

        btnCalculate = findViewById(R.id.btn_purchase);
        amount = findViewById(R.id.etxt_amount1);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addAlarm(amount.getText().toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        Toast.makeText(this, "aooliance" + appliance.get(get), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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