package com.electricity.monitoring.threshold;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        getSupportActionBar().setTitle("Calculate Threshold");

        dbHandler = new DBHandler(CalculateThresholdActivity.this);

        ArrayList<Appliance> applianceArrayList;
        applianceArrayList = dbHandler.getAppliances();

        appliance = new ArrayList<String>();

//        for (int i = 0; i < applianceArrayList.size(); i++) {
//        }

            for (Appliance appliance1 : applianceArrayList
            ) {
                appliance.add(appliance1.getApplianceName());
            }

        optionList = new String[appliance.size()];

        for (int i = 0; i < appliance.size(); i++) {
            optionList[i] = appliance.get(i);
        }

        paymentOption = findViewById(R.id.payment_option);
        btnCalculate = findViewById(R.id.btn_purchase);
        lastCalculated = findViewById(R.id.calculated_last);
        givenCalculated = findViewById(R.id.calculated_given);
        amount = findViewById(R.id.etxt_amount);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applianceData = dbHandler.getAppliancesByName(paymentOption.getSelectedItem().toString());

                String threshold = dbHandler.checkThreshold();
                String consumption = applianceData.get(0).getApplianceConsumption();
                Long cal_cons = Long.parseLong(consumption);
                Long cal_thre = Long.parseLong(threshold);
                Long cal_time = (Long.parseLong(amount.getText().toString().trim())) / 60;

//                int hours = (int) ((cal_thre / cal_cons) / (60));
//                int min = (int) ((cal_thre / cal_cons) - (60 * hours)) /  60;
//                String duration = hours + "hrs " + min + "mins";
//
//                int hours1 = (int) ((cal_thre / cal_cons) / (1000 * 60 * 60));
//                int min1 = (int) ((cal_thre / cal_cons) - (1000 * 60 * 60 * hours)) / (1000 * 60);
//                String duration1 = hours1 + "hrs " + min1 + "mins";

                lastCalculated.setText("With available threshold, it can last for : " + cal_thre / cal_cons + " minutes");
                givenCalculated.setText("Will use : " + cal_time * cal_cons + " kWh");

//                Toast.makeText(CalculateThresholdActivity.this, amount.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentOption.setAdapter(adapter);
        paymentOption.setOnItemSelectedListener(this);
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
}