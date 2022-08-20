package com.electricity.monitoring.tokens;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Token;
import com.electricity.monitoring.model.User;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseTokenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] optionList = {"Ecocash", "Bank Transfer"};
    Spinner paymentOption;
    TextView btnPurchase;
    EditText amount;
    DBHandler dbHandler;
    ArrayList<User> userArrayList;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_token);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Purchase Token");

        paymentOption = findViewById(R.id.payment_option);
        btnPurchase = findViewById(R.id.btn_purchase);
        amount = findViewById(R.id.etxt_amount);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(PurchaseTokenActivity.this);
                dialogBuilder
                        .withTitle("Purchase")
                        .withMessage("Proceed with purchase ?")
                        .withEffect(Slidetop)
                        .withDialogColor("#637ECF") //use color code for dialog
                        .withButton1Text(getString(R.string.yes))
                        .withButton2Text(getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loading = new ProgressDialog(PurchaseTokenActivity.this);
                                loading.setCancelable(false);
                                loading.setMessage(getString(R.string.please_wait));
                                loading.show();

                                dbHandler = new DBHandler(PurchaseTokenActivity.this);
                                userArrayList = dbHandler.getUser();
                                String client_id = userArrayList.get(0).getUserID();
                                String amount_paid = amount.getText().toString();

                                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                                Call<Token> call = apiInterface.purchasetoken(client_id, amount_paid);

                                call.enqueue(new Callback<Token>() {
                                    @Override
                                    public void onResponse(Call<Token> call, Response<Token> response) {
                                        loading.dismiss();
                                        if (response.code() == 200) {
                                            Toasty.success(PurchaseTokenActivity.this, "Purchased successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), PurchasedTokensActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            dialogBuilder.dismiss();
                                            finish();
                                        } else {
                                            Toasty.error(PurchaseTokenActivity.this, "Failed purchasing", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Token> call, Throwable t) {
                                        loading.dismiss();
                                        Toasty.error(PurchaseTokenActivity.this, "Something went wrong " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentOption.setAdapter(adapter);
        paymentOption.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}