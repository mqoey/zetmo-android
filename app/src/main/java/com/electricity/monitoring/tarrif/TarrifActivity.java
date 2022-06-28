package com.electricity.monitoring.tarrif;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Tarrif;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarrifActivity extends AppCompatActivity {

    TextView txtprice, txtdate;
    ProgressDialog loading;
    DBHandler dbHandler;
    ArrayList<Tarrif> tarrifArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarrif);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.tarrif);

        txtprice = findViewById(R.id.txt_price);
        txtdate = findViewById(R.id.txt_date);

        dbHandler = new DBHandler(TarrifActivity.this);
        fetchTarrifs();
    }

    public void fetchTarrifs() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Tarrif> call = apiInterface.getTarrif();
        call.enqueue(new Callback<Tarrif>() {
            @Override
            public void onResponse(@NonNull Call<Tarrif> call, @NonNull Response<Tarrif> response) {
                loading.dismiss();
                if (response.code() == 200) {

                    String tarrifID = response.body().getId();
                    String price = response.body().getPrice();
                    String date = response.body().getDate();

                    txtdate.setText(date);
                    txtprice.setText("$" + price + " /1KHz");

                    dbHandler.fetchTarrifs(date, price, tarrifID);
                }
            }

            @Override
            public void onFailure(Call<Tarrif> call, Throwable t) {
                loading.dismiss();
                tarrifArrayList = dbHandler.getTarrif();

                String price = tarrifArrayList.get(0).getPrice();
                String date = tarrifArrayList.get(0).getDate();

                txtdate.setText(date);
                txtprice.setText("$" + price + " /1KHz");
                Toasty.error(TarrifActivity.this, "No Internet Connectivity", Toasty.LENGTH_LONG).show();
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