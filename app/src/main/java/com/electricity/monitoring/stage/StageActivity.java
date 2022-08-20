package com.electricity.monitoring.stage;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.model.Stage;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StageActivity extends AppCompatActivity {

    String areaID;
    ProgressDialog loading;
    LinearLayout stage1, stage2, stage3, stage4, stage5, stage6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        stage1 = (LinearLayout) findViewById(R.id.layout_stage1);
        stage2 = (LinearLayout) findViewById(R.id.layout_stage2);
        stage3 = (LinearLayout) findViewById(R.id.layout_stage3);
        stage4 = (LinearLayout) findViewById(R.id.layout_stage4);
        stage5 = (LinearLayout) findViewById(R.id.layout_stage5);
        stage6 = (LinearLayout) findViewById(R.id.layout_stage6);

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

                    if (stageId == null) {
                        stage1.setBackgroundColor(Color.GRAY);
                    } else if (stageId.equals("2")) {
                        stage2.setBackgroundColor(Color.GRAY);
                    } else if (stageId.equals("3")) {
                        stage3.setBackgroundColor(Color.GRAY);
                    } else if (stageId.equals("4")) {
                        stage4.setBackgroundColor(Color.GRAY);
                    } else if (stageId.equals("5")) {
                        stage5.setBackgroundColor(Color.GRAY);
                    } else if (stageId.equals("6")) {
                        stage6.setBackgroundColor(Color.GRAY);
                    } else {
                        stage1.setBackgroundColor(Color.GRAY);
                    }
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