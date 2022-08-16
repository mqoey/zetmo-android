package com.electricity.monitoring.neighbourhood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.NeighbourhoodAdapter;
import com.electricity.monitoring.adapter.NeighbourhoodStageAdapter;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Neighbourhood;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeighbourhoodStageActivity extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText etxtSearch;
    ProgressDialog loading;
    private RecyclerView recyclerView;
    private NeighbourhoodStageAdapter neighbourhoodStageAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    public String userID;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbourhood_stage);

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);

        getNeighbourhoods();
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
                loading.dismiss();

                if (response.isSuccessful()) {

                    ArrayList<Neighbourhood> neighbourhoodArrayList;
                    neighbourhoodArrayList = response.body();

//                    Toasty.success(NeighbourhoodActivity.this, "Got data " + neighbourhoodArrayList.size(), Toast.LENGTH_SHORT).show();

                    if (neighbourhoodArrayList.isEmpty()) {

                        recyclerView.setVisibility(View.GONE);
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        Toasty.info(NeighbourhoodStageActivity.this, "No Neighbourhoods", Toast.LENGTH_SHORT).show();

                    } else {
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        recyclerView.setVisibility(View.VISIBLE);
                        neighbourhoodStageAdapter = new NeighbourhoodStageAdapter(neighbourhoodArrayList, NeighbourhoodStageActivity.this);
                        recyclerView.setAdapter(neighbourhoodStageAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Neighbourhood>> call, Throwable t) {
                loading.dismiss();
                Toasty.error(NeighbourhoodStageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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