package com.electricity.monitoring.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.model.User;
import com.electricity.monitoring.utils.Utils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    EditText etxtEmail, etxtPassword, etxt_Address, etxt_MeterNumber, etxt_FirstName, etxt_LastName, etxt_ConfirmPassword;
    TextView txtLogin, txtRegister;
    Utils utils;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        etxtEmail = findViewById(R.id.etxt_email1);
        etxtPassword = findViewById(R.id.etxt_password1);
        etxt_Address = findViewById(R.id.etxt_address);
        etxt_MeterNumber = findViewById(R.id.etxt_meter_number);
        etxt_FirstName = findViewById(R.id.etxt_firstname);
        etxt_LastName = findViewById(R.id.etxt_lastname);
        etxt_ConfirmPassword = findViewById(R.id.etxt_password_confirmation);
        txtLogin = findViewById(R.id.txt_login1);
        txtRegister = findViewById(R.id.txt_register1);

        utils = new Utils();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = etxtEmail.getText().toString().trim();
                String password1 = etxtPassword.getText().toString().trim();
                String password2 = etxt_ConfirmPassword.getText().toString().trim();
                String meter_number1 = etxt_MeterNumber.getText().toString().trim();
                String address1 = etxt_Address.getText().toString().trim();
                String first_name1 = etxt_FirstName.getText().toString().trim();
                String last_name1 = etxt_LastName.getText().toString().trim();

                if (first_name1.isEmpty()) {
                    etxt_FirstName.setError(getString(R.string.please_enter_firstname));
                    etxt_FirstName.requestFocus();
                } else if (last_name1.isEmpty()) {
                    etxt_LastName.setError(getString(R.string.please_enter_lastname));
                    etxt_LastName.requestFocus();
                } else if (email1.isEmpty() || !email1.contains("@") || !email1.contains(".")) {
                    etxtEmail.setError(getString(R.string.enter_valid_email));
                    etxtEmail.requestFocus();
                } else if (meter_number1.isEmpty()) {
                    etxt_MeterNumber.setError(getString(R.string.please_enter_meternumber));
                    etxt_MeterNumber.requestFocus();
                } else if (address1.isEmpty()) {
                    etxt_Address.setError(getString(R.string.please_enter_address));
                    etxt_Address.requestFocus();
                } else if (password1.isEmpty()) {
                    etxtPassword.setError(getString(R.string.please_enter_password));
                    etxtPassword.requestFocus();
                } else if (password2.isEmpty()) {
                    etxt_ConfirmPassword.setError(getString(R.string.please_enter_password_confirmation));
                    etxt_ConfirmPassword.requestFocus();
                } else if (!password1.equals(password2)) {
                    etxt_ConfirmPassword.setError(getString(R.string.password_dont_match));
                    etxt_ConfirmPassword.requestFocus();
                } else {
                    if (utils.isNetworkAvailable(RegistrationActivity.this)) {
                        register(email1, password1, address1, meter_number1, first_name1, last_name1);
                    } else {
                        Toasty.error(RegistrationActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //registration method
    private void register(String email, String password, String address, String meter_number, String first_name, String last_name) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.register(email, password, address, meter_number, first_name, last_name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    Toasty.success(RegistrationActivity.this, "Registered successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                    Intent intent = new Intent(RegistrationActivity.this, HomeRegistrationActivity.class);
//                    intent.putExtra("USERID", response.body().getId());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toasty.error(RegistrationActivity.this, "Email or meter number already registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loading.dismiss();
                Toasty.error(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}