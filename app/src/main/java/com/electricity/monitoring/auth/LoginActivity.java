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
import com.electricity.monitoring.model.Login;
import com.electricity.monitoring.networking.ApiClient;
import com.electricity.monitoring.networking.ApiInterface;
import com.electricity.monitoring.utils.BaseActivity;
import com.electricity.monitoring.utils.Utils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    EditText etxtEmail, etxtPassword;
    TextView txtLogin, txtRegister;
    SharedPreferences sp;
    ProgressDialog loading;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etxtEmail = findViewById(R.id.etxt_email);
        etxtPassword = findViewById(R.id.etxt_password);
        txtLogin = findViewById(R.id.txt_login);
        txtRegister = findViewById(R.id.txt_register);
        utils = new Utils();

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String email = sp.getString(Constant.SP_EMAIL, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");

        etxtEmail.setText(email);
        etxtPassword.setText(password);

        if (email.length() >= 3 && password.length() >= 3) {
            if (utils.isNetworkAvailable(LoginActivity.this)) {
                login(email, password);
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
            String email1 = etxtEmail.getText().toString().trim();
            String password1 = etxtPassword.getText().toString().trim();

            if (email1.isEmpty() || !email1.contains("@") || !email1.contains(".")) {
                etxtEmail.setError(getString(R.string.enter_valid_email));
                etxtEmail.requestFocus();
            } else if (password1.isEmpty()) {
                etxtPassword.setError(getString(R.string.please_enter_password));
                etxtPassword.requestFocus();
            } else {
                if (utils.isNetworkAvailable(LoginActivity.this)) {
                    login(email1, password1);
                } else {
                    Toasty.error(LoginActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //login method
    private void login(String email, String password) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Login> call = apiInterface.login(email, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                loading.dismiss();
                if(response.code() == 200) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Constant.SP_EMAIL, email);
                            editor.putString(Constant.SP_PASSWORD, password);
                            editor.apply();

                    Toasty.success(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toasty.error(LoginActivity.this, R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                loading.dismiss();
                Toasty.error(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
