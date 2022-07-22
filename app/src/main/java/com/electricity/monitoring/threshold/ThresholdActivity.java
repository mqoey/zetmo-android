package com.electricity.monitoring.threshold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;

public class ThresholdActivity extends AppCompatActivity {

    TextView threshold;
    Button calculate;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threshold);

        threshold = findViewById(R.id.txt_threshold);
        calculate = findViewById(R.id.btn_calculate);
        dbHandler = new DBHandler(ThresholdActivity.this);

        threshold.setText(new StringBuilder().append(dbHandler.checkThreshold()).append(" KWh").toString());

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Threshold");

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThresholdActivity.this, CalculateThresholdActivity.class);
                startActivity(intent);
            }
        });
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