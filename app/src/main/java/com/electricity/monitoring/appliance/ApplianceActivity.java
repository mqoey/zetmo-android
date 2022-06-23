package com.electricity.monitoring.appliance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.ApplianceAdapter;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.utils.BaseActivity;
import com.electricity.monitoring.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ApplianceActivity extends BaseActivity {

    private RecyclerView recyclerView;
    ApplianceAdapter applianceAdapter;
    DBHandler dbHandler;

    ImageView imgNoProduct;
    EditText etxtSearch;

    FloatingActionButton fabAdd;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance);

        fabAdd = findViewById(R.id.fab_add);
        etxtSearch = findViewById(R.id.etxt_search);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.appliances);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout =findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = findViewById(R.id.product_recyclerview);
        imgNoProduct = findViewById(R.id.image_no_product);

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplianceActivity.this, AddApplianceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Utils utils=new Utils();
        dbHandler = new DBHandler(ApplianceActivity.this);

        //swipe refresh listeners
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            if (utils.isNetworkAvailable(ApplianceActivity.this))
            {
//                getProductsData("",shopID,ownerId);
            }
            else
            {
                Toasty.error(ApplianceActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }


            //after shuffle id done then swife refresh is off
            mSwipeRefreshLayout.setRefreshing(false);
        });


        if (utils.isNetworkAvailable(ApplianceActivity.this))
        {
//            //Load data from server
//            getProductsData("",shopID,ownerId);
        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            //Stopping Shimmer Effects
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
        }

        ArrayList<Appliance> applianceArrayList;
        applianceArrayList = dbHandler.getAppliances();


        if (applianceArrayList.isEmpty()) {

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
            applianceAdapter = new ApplianceAdapter(ApplianceActivity.this, applianceArrayList);

            recyclerView.setAdapter(applianceAdapter);
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