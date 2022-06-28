package com.electricity.monitoring.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.model.ClientNeighbourhood;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientNeighbourhoodActivity extends AppCompatActivity {

    String userID, neighbourhoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_neighbourhood);
        userID = getIntent().getExtras().getString("USERID");
        neighbourhoodID = getIntent().getExtras().getString("NEIGHBOURHOODID");
        Toasty.success(ClientNeighbourhoodActivity.this, "userid ++ " + userID + "  neighbou ++ " + neighbourhoodID, Toasty.LENGTH_LONG).show();
        postClientNeighbourhood(userID, neighbourhoodID);
    }

    public void postClientNeighbourhood(String clientID, String neighbourhoodID){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ClientNeighbourhood> call = apiInterface.clientneighbourhood(clientID, neighbourhoodID);
        call.enqueue(new Callback<ClientNeighbourhood>() {
            @Override
            public void onResponse(Call<ClientNeighbourhood> call, Response<ClientNeighbourhood> response) {
                if (response.code() == 200){
                    Intent intent = new Intent(ClientNeighbourhoodActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ClientNeighbourhood> call, Throwable t) {

            }
        });
    }
}