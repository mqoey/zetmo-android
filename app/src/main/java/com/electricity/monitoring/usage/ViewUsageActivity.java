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

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.ViewReportAdapter;
import com.electricity.monitoring.adapter.ViewUsageAdapter;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.ApplianceTime;
import com.electricity.monitoring.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class ViewUsageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ViewUsageAdapter viewUsageAdapter;
    DBHandler dbHandler;

    ImageView imgNoProduct;
    EditText etxtSearch;

    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_usage);

        etxtSearch = findViewById(R.id.etxt_search);

        Utils utils=new Utils();
        dbHandler = new DBHandler(ViewUsageActivity.this);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Usage");

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout =findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = findViewById(R.id.product_recyclerview);
        imgNoProduct = findViewById(R.id.image_no_view_appliance);

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);
        ArrayList<ApplianceTime> applianceTimeArrayList;

        String date = getIntent().getExtras().getString(Constant.DATE);

        dbHandler = new DBHandler(ViewUsageActivity.this);
        applianceTimeArrayList = dbHandler.getAppliancesByDate(date);

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
            viewUsageAdapter = new ViewUsageAdapter(ViewUsageActivity.this, applianceTimeArrayList);

            recyclerView.setAdapter(viewUsageAdapter);
        }

        etxtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 1) {

//                    //search data from server
//                    getProductsData(s.toString(),shopID,ownerId);
//                } else {
//                    getProductsData("",shopID,ownerId);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
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