package com.electricity.monitoring.usage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.UsageAdapter;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.model.ApplianceTime;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class UsageActivity extends AppCompatActivity {

    UsageAdapter usageAdapter;
    DBHandler dbHandler;
    ImageView imgNoProduct;
    EditText etxtSearch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        etxtSearch = findViewById(R.id.etxt_search);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Usage");

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = findViewById(R.id.product_recyclerview);
        imgNoProduct = findViewById(R.id.image_no_product);

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);
        ArrayList<ApplianceTime> applianceTimeArrayList;
        dbHandler = new DBHandler(UsageActivity.this);
        applianceTimeArrayList = dbHandler.getApplianceDate();

        if (applianceTimeArrayList.isEmpty()) {

            recyclerView.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            //Stopping Shimmer Effects
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);

        } else {
            //Stopping Shimmer Effects
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);
            imgNoProduct.setVisibility(View.GONE);
            usageAdapter = new UsageAdapter(UsageActivity.this, applianceTimeArrayList);

            recyclerView.setAdapter(usageAdapter);
        }

        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Appliance> applianceArrayList1;
                if (s.length() > 1) {
//                    //search data from server
                    applianceArrayList1 = dbHandler.getApplianceSearch(s.toString());
                } else {
                    applianceArrayList1 = dbHandler.getApplianceSearch("");
                }

                if (applianceArrayList1.isEmpty()) {

                    recyclerView.setVisibility(View.GONE);
                    imgNoProduct.setVisibility(View.VISIBLE);
                    imgNoProduct.setImageResource(R.drawable.not_found);
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);

                } else {
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imgNoProduct.setVisibility(View.GONE);
                    usageAdapter = new UsageAdapter(UsageActivity.this, applianceTimeArrayList);
                    recyclerView.setAdapter(usageAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
            }
        });
//        etxtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("data", s.toString());
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ArrayList<Appliance> applianceArrayList1;
//                if (s.length() > 1) {
////                    //search data from server
//                    applianceArrayList1 = dbHandler.getApplianceSearch(s.toString());
//                } else {
//                    applianceArrayList1 = dbHandler.getApplianceSearch("");
//                }
//
//                if (applianceArrayList1.isEmpty()) {
//
//                    recyclerView.setVisibility(View.GONE);
//                    imgNoProduct.setVisibility(View.VISIBLE);
//                    imgNoProduct.setImageResource(R.drawable.not_found);
//                    mShimmerViewContainer.stopShimmer();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//
//                } else {
//                    mShimmerViewContainer.stopShimmer();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    imgNoProduct.setVisibility(View.GONE);
//                    usageAdapter = new UsageAdapter(UsageActivity.this, applianceTimeArrayList);
//                    recyclerView.setAdapter(usageAdapter);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d("data", s.toString());
//            }
//        });
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