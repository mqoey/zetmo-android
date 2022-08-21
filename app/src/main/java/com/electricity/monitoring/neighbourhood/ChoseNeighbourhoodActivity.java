package com.electricity.monitoring.neighbourhood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class ChoseNeighbourhoodActivity extends AppCompatActivity {

    public String userID;
    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText etxtSearch;
    ProgressDialog loading;
    DBHandler dbHandler;
    private RecyclerView recyclerView;
    private NeighbourhoodAdapter neighbourhoodAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private NeighbourhoodStageAdapter neighbourhoodStageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_neighbourhood);


        recyclerView = findViewById(R.id.neighbourhood_recyclerview);
        etxtSearch = findViewById(R.id.etxt_search2);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Your Neighbourhoods");

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
        String userID, neighbourhoodID;
        DBHandler dbHandler = new DBHandler(ChoseNeighbourhoodActivity.this);
        ProgressDialog loading = new ProgressDialog(ChoseNeighbourhoodActivity.this);

//        neighbourhoodID = neighbourhoodData.get(getAdapterPosition()).getId();
        userID = dbHandler.loggedInUserID();

        loading.setCancelable(false);
        loading.setMessage(ChoseNeighbourhoodActivity.this.getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Neighbourhood>> call2 = apiInterface.getClientNeighbourhood(userID);
        call2.enqueue(new Callback<ArrayList<Neighbourhood>>() {
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
                        Toasty.info(ChoseNeighbourhoodActivity.this, "No Neighbourhoods", Toast.LENGTH_SHORT).show();

                    } else {
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        recyclerView.setVisibility(View.VISIBLE);
//                        neighbourhoodAdapter = new NeighbourhoodAdapter(neighbourhoodArrayList, ChoseNeighbourhoodActivity.this);
//                        recyclerView.setAdapter(neighbourhoodAdapter);
                        neighbourhoodStageAdapter = new NeighbourhoodStageAdapter(neighbourhoodArrayList, ChoseNeighbourhoodActivity.this);
                        recyclerView.setAdapter(neighbourhoodStageAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Neighbourhood>> call, Throwable t) {
                loading.dismiss();
                Toasty.error(ChoseNeighbourhoodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public String getUserID(){
//        return userID;
//    }


    public String getUserID() {
        Toasty.info(ChoseNeighbourhoodActivity.this, "user : " + userID, Toasty.LENGTH_LONG).show();
        return userID;
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