package com.electricity.monitoring.tokens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.HomeActivity;
import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.model.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTokenActivity extends AppCompatActivity {

    TextView name, number, power, amount, address, meter_number;
    String txt_client_name, txt_token_number, txt_power_bought, txt_amount_paid, txt_meter_number, txt_address, txt_id;
    Button done;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_token);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("View Token");

        name = findViewById(R.id.txt_client_name);
        number = findViewById(R.id.txt_token_number);
        power = findViewById(R.id.txt_power_bought);
        amount = findViewById(R.id.txt_amount_paid);
        address = findViewById(R.id.txt_address);
        meter_number = findViewById(R.id.txt_meter_number);
        done = findViewById(R.id.btn_done);

        txt_id = getIntent().getExtras().getString("TOKEN_ID");
        txt_client_name = getIntent().getExtras().getString("CLIENT");
        txt_token_number = getIntent().getExtras().getString("TOKEN_NUMBER");
        txt_power_bought = getIntent().getExtras().getString("POWER_BOUGHT");
        txt_amount_paid = getIntent().getExtras().getString("AMOUNT_PAID");
        txt_meter_number = getIntent().getExtras().getString("METER_NUMBER");
        txt_address = getIntent().getExtras().getString("ADDRESS");

        name.setText("Name : " + txt_client_name);
        number.setText("Token Number " + txt_token_number);
        power.setText("Energy Bought : " + txt_power_bought + " KHz");
        amount.setText("Amount Paid : $" + txt_amount_paid);
        address.setText("Address : " + txt_address);
        meter_number.setText("Meter Number : " + txt_meter_number);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = new ProgressDialog(ViewTokenActivity.this);
                loading.setCancelable(false);
                loading.setMessage(getString(R.string.please_wait));
                loading.show();

                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<Token> call = apiInterface.useToken(txt_id);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        loading.dismiss();
                        if (response.code() == 200){
                            Intent intent = new Intent(ViewTokenActivity.this, TokenActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        loading.dismiss();
                    }
                });
            }
        });
    }
}