package com.electricity.monitoring.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.NeighbourhoodAdapter;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.model.Neighbourhood;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRegistrationActivity extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText etxtSearch;
    private RecyclerView recyclerView;
    private ArrayList<Neighbourhood> neighbourhoodArrayList;
    private NeighbourhoodAdapter neighbourhoodAdapter;
    ProgressDialog loading;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_registration);

        recyclerView = findViewById(R.id.neighbourhood_recyclerview);
        etxtSearch = findViewById(R.id.etxt_search2);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Choose Neighbourhood");

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        neighbourhoodArrayList = new ArrayList<>();
        getNeighbourhoods();
//        recyclerView.setVisibility(View.VISIBLE);

    }

    private void getNeighbourhoods() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Neighbourhood>> call = apiInterface.getNeighbourhoods();
        call.enqueue(new Callback<ArrayList<Neighbourhood>>() {
            @Override
            public void onResponse(Call<ArrayList<Neighbourhood>> call, Response<ArrayList<Neighbourhood>> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    neighbourhoodArrayList = response.body();
                    for (int i = 0; i < neighbourhoodArrayList.size(); i++) {
                        neighbourhoodAdapter = new NeighbourhoodAdapter(neighbourhoodArrayList, HomeRegistrationActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeRegistrationActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(neighbourhoodAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Neighbourhood>> call, Throwable t) {
                loading.dismiss();
                System.out.println("----mq----error");
                System.out.println("neighb" + t.getMessage());

                Toasty.error(HomeRegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public ArrayList<Neighbourhood> getNeighbourhoods() {
//        loading = new ProgressDialog(this);
//        loading.setCancelable(false);
//        loading.setMessage(getString(R.string.please_wait));
//        loading.show();
//
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<ArrayList<Neighbourhood>> call = apiInterface.getNeighbourhoods();
//        call.enqueue(new Callback<ArrayList<Neighbourhood>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Neighbourhood>> call, Response<ArrayList<Neighbourhood>> response) {
//                loading.dismiss();
//                System.out.println("----mq----");
//                if (response.code() == 200) {
//                    ArrayList<Neighbourhood> neighbourhoodArrayList = response.body();
//                    System.out.println("neighb" + neighbourhoods.get(0));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Neighbourhood>> call, Throwable t) {
//                loading.dismiss();
//                System.out.println("----mq----error");
//                System.out.println("neighb" + t.getMessage());
//
//                Toasty.error(HomeRegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return neighbourhoodArrayList;
//    }


}