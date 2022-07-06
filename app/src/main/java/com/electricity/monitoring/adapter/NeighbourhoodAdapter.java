package com.electricity.monitoring.adapter;

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
import com.electricity.monitoring.R;
import com.electricity.monitoring.appliance.ViewApplianceActivity;
import com.electricity.monitoring.auth.ClientNeighbourhoodActivity;
import com.electricity.monitoring.auth.HomeRegistrationActivity;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.User;
import com.electricity.monitoring.utils.Utils;

import java.util.ArrayList;

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

            HomeRegistrationActivity homeRegistrationActivity = new HomeRegistrationActivity();
            userID = homeRegistrationActivity.getUserID();
            neighbourhoodID = neighbourhoodData.get(getAdapterPosition()).getId();

            Intent i = new Intent(context, ClientNeighbourhoodActivity.class);
//            i.putExtra("USERID", userID);
            i.putExtra("NEIGHBOURHOODID", neighbourhoodID);
            context.startActivity(i);
        }
    }
}
