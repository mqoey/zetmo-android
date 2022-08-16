package com.electricity.monitoring.stage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.adapter.NeighbourhoodAdapter;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.Stage;
import com.electricity.monitoring.neighbourhood.NeighbourhoodActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StageActivity extends AppCompatActivity {

    String areaID;
    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText etxtSearch;
    ProgressDialog loading;
    private RecyclerView recyclerView;
    private NeighbourhoodAdapter neighbourhoodAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    public String userID;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Stage Neighbourhood");

        areaID = getIntent().getExtras().getString(Constant.AREA_ID);
getNeighbourhoodStage(areaID);
    }

    private void getNeighbourhoodStage(String neighbourhoodID) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Stage> call = apiInterface.getneighbourhoodstage(neighbourhoodID);
        call.enqueue(new Callback<Stage>() {
            @Override
            public void onResponse(Call<Stage> call, Response<Stage> response) {
                loading.dismiss();
                if (response.code() == 200) {

                    String stageId = response.body().getStage_id();
//                    Toasty.success(this, "Stage : " + stageId, Toast.LENGTH_SHORT).show();
                    Toast.makeText(StageActivity.this, "Stage : " + stageId, Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onFailure(Call<Stage> call, Throwable t) {
                loading.dismiss();
                Toasty.error(StageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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