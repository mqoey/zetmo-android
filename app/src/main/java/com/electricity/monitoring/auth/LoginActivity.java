package com.electricity.monitoring.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.HomeActivity;
import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.User;
import com.electricity.monitoring.utils.BaseActivity;
import com.electricity.monitoring.utils.Utils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    EditText etxtMeterNumber, etxtPassword;
    TextView txtLogin, txtRegister;
    SharedPreferences sp;
    ProgressDialog loading;
    Utils utils;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etxtMeterNumber = findViewById(R.id.etxt_email);
        etxtPassword = findViewById(R.id.etxt_password);
        txtLogin = findViewById(R.id.txt_login);
        txtRegister = findViewById(R.id.txt_register);
        utils = new Utils();

        dbHandler = new DBHandler(LoginActivity.this);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String meter_number = sp.getString(Constant.SP_METER_NUMBER, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");

        etxtMeterNumber.setText(meter_number);
        etxtPassword.setText(password);

        if (meter_number.length() >= 3 && password.length() >= 3) {
            if (utils.isNetworkAvailable(LoginActivity.this)) {
                login(meter_number, password);
            } else {
                Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
        }

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        txtLogin.setOnClickListener(v -> {
            String meter_number1 = etxtMeterNumber.getText().toString().trim();
            String password1 = etxtPassword.getText().toString().trim();

            if (meter_number1.isEmpty()) {
                etxtMeterNumber.setError(getString(R.string.please_enter_meternumber));
                etxtMeterNumber.requestFocus();
            } else if (password1.isEmpty()) {
                etxtPassword.setError(getString(R.string.please_enter_password));
                etxtPassword.requestFocus();
            } else {
                if (utils.isNetworkAvailable(LoginActivity.this)) {
                    login(meter_number1, password1);
                } else {
                    Toasty.error(LoginActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //login method
    private void login(String meter_number, String password) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.login(meter_number, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loading.dismiss();
                if (response.code() == 200) {

                    String userID = response.body().getId();
                    String name = response.body().getName();
                    String email = response.body().getEmail();
                    String password = response.body().getPassword();
                    String address = response.body().getAddress();
                    String meterNumber = response.body().getMeter_number();

                    dbHandler.loginUser(userID, name, email, password, address, meterNumber);

                    Toasty.success(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toasty.error(LoginActivity.this, R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loading.dismiss();
                Toasty.error(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
