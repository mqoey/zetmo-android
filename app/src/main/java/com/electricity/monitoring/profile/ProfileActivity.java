package com.electricity.monitoring.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.R;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.User;
import com.electricity.monitoring.neighbourhood.ChoseNeighbourhoodActivity;
import com.electricity.monitoring.neighbourhood.NeighbourhoodActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView txt_name, txt_email, txt_address, txt_meter_number, txt_neighbourhood, txt_area, txt_municipality;
    DBHandler dbHandler;
    ArrayList<User> userArrayList;
    ArrayList<Neighbourhood> neighbourhoodArrayList;
    Button btnChooseNeighbourhood, btnYourNeighbourhood;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnChooseNeighbourhood = findViewById(R.id.btn_choose_neighbourhood);
        btnYourNeighbourhood = findViewById(R.id.btn_your_neighbourhoods);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Profile");

        btnChooseNeighbourhood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, NeighbourhoodActivity.class);
                startActivity(intent);
            }
        });

        btnYourNeighbourhood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChoseNeighbourhoodActivity.class);
                startActivity(intent);
            }
        });

        txt_name = findViewById(R.id.txt_user_name);
        txt_email = findViewById(R.id.txt_user_email);
        txt_address = findViewById(R.id.txt_user_address);
        txt_meter_number = findViewById(R.id.txt_user_meter_number);
        txt_neighbourhood = findViewById(R.id.txt_neighbourhood_name);
        txt_area = findViewById(R.id.txt_neighbourhood_area);
        txt_municipality = findViewById(R.id.txt_neighbourhood_municipality);

        dbHandler = new DBHandler(ProfileActivity.this);

        userArrayList = dbHandler.getUser();
        neighbourhoodArrayList = dbHandler.getNeighbourhood();

//        boolean neighbourhoodStatus = dbHandler.checkNeighbourhood();
//        if (neighbourhoodStatus) {
//            btnChooseNeighbourhood.setEnabled(false);
//            txt_neighbourhood.setText("Neighbourhood : " + neighbourhoodArrayList.get(0).getName());
//            txt_area.setText("Area : " + neighbourhoodArrayList.get(0).getArea());
//            txt_municipality.setText("Municipality : " + neighbourhoodArrayList.get(0).getMunicipality());
//        } else {
//            txt_neighbourhood.setText("");
//            txt_area.setText("");
//            txt_municipality.setText("");
//        }

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