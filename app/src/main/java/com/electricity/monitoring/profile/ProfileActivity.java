package com.electricity.monitoring.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView txt_name, txt_email, txt_address, txt_meter_number;
    DBHandler dbHandler;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Profile");

        txt_name = findViewById(R.id.txt_user_name);
        txt_email = findViewById(R.id.txt_user_email);
        txt_address = findViewById(R.id.txt_user_address);
        txt_meter_number = findViewById(R.id.txt_user_meter_number);

        dbHandler = new DBHandler(ProfileActivity.this);

        userArrayList = dbHandler.getUser();

        String name = userArrayList.get(0).getName();
        String email = userArrayList.get(0).getEmail();
        String address = userArrayList.get(0).getAddress();
        String meter_number = userArrayList.get(0).getMeter_number();

        txt_name.setText("Name : " + name);
        txt_email.setText("Email : " + email);
        txt_address.setText("Address : " + address);
        txt_meter_number.setText("Meter number : " + meter_number);
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