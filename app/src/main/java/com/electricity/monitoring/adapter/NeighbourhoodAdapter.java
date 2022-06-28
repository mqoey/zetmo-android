package com.electricity.monitoring.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electricity.monitoring.R;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.utils.Utils;

import java.util.ArrayList;

public class NeighbourhoodAdapter extends RecyclerView.Adapter<NeighbourhoodAdapter.MyViewHolder> {

    private final ArrayList<Neighbourhood> neighbourhoodData;
    Utils utils;

    public NeighbourhoodAdapter(ArrayList<Neighbourhood> neighbourhoodData, Context context) {
        this.utils = new Utils();
        this.neighbourhoodData = neighbourhoodData;
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

        }
    }
}
