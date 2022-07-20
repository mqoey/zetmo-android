package com.electricity.monitoring.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.HomeActivity;
import com.electricity.monitoring.R;
import com.electricity.monitoring.api.ApiClient;
import com.electricity.monitoring.api.ApiInterface;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.ClientNeighbourhood;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.profile.ProfileActivity;
import com.electricity.monitoring.utils.Utils;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeighbourhoodAdapter extends RecyclerView.Adapter<NeighbourhoodAdapter.MyViewHolder> {

    private final ArrayList<Neighbourhood> neighbourhoodData;
    private final Context context;
    Utils utils;

    public NeighbourhoodAdapter(ArrayList<Neighbourhood> neighbourhoodData, Context context) {
        this.utils = new Utils();
        this.context = context;
        this.neighbourhoodData = neighbourhoodData;
        SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public NeighbourhoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neighbourhood_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NeighbourhoodAdapter.MyViewHolder holder, int position) {

        Neighbourhood neighbourhood = neighbourhoodData.get(position);

        String name = neighbourhood.getName();
        String area = neighbourhood.getArea();
        String municipality = neighbourhood.getMunicipality();

        holder.txt_name.setText(name);
        holder.txt_municipality.setText(area);
        holder.txt_area.setText(municipality);
    }

    @Override
    public int getItemCount() {
        return neighbourhoodData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name, txt_area, txt_municipality;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_neighbourhood_name);
            txt_area = itemView.findViewById(R.id.txt_neighbourhood_area);
            txt_municipality = itemView.findViewById(R.id.txt_neighbourhood_municipality);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String userID, neighbourhoodID;
            DBHandler dbHandler = new DBHandler(context);
            ProgressDialog loading = new ProgressDialog(context);

            neighbourhoodID = neighbourhoodData.get(getAdapterPosition()).getId();
            userID = dbHandler.loggedInUserID();

            loading.setCancelable(false);
            loading.setMessage(context.getString(R.string.please_wait));
            loading.show();

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ClientNeighbourhood> call = apiInterface.clientneighbourhood(userID, neighbourhoodID);
            call.enqueue(new Callback<ClientNeighbourhood>() {
                @Override
                public void onResponse(Call<ClientNeighbourhood> call, Response<ClientNeighbourhood> response) {
                    loading.dismiss();
                    if (response.code() == 200) {
                        Toasty.success(context, "Choose neighbourhood successful ", Toast.LENGTH_SHORT).show();
                        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<ArrayList<Neighbourhood>> call2 = apiInterface.getClientNeighbourhood(userID);
                        call2.enqueue(new Callback<ArrayList<Neighbourhood>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Neighbourhood>> call, Response<ArrayList<Neighbourhood>> response) {
                                loading.dismiss();
                                if (response.code() == 200) {

                                    ArrayList<Neighbourhood> neighbourhoodArrayList;
                                    neighbourhoodArrayList = response.body();

                                    String neighbourhood = neighbourhoodArrayList.get(0).getName();
                                    String area = neighbourhoodArrayList.get(0).getArea();
                                    String municipality = neighbourhoodArrayList.get(0).getMunicipality();

                                    dbHandler.choseNeighbourhood(neighbourhood, area, municipality);

                                } else {
                                    Toasty.error(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Neighbourhood>> call, Throwable t) {
                                loading.dismiss();
                                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent i = new Intent(context, HomeActivity.class);
                        context.startActivity(i);
                    } else {
                        Toasty.error(context, "Failed choosing", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClientNeighbourhood> call, Throwable t) {
                    loading.dismiss();
                    Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
