package com.electricity.monitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.appliance.ViewApplianceActivity;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.MyViewHolder> {

    private final ArrayList<Appliance> applianceData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;

    public ApplianceAdapter(Context context, ArrayList<Appliance> applianceData) {
        this.context = context;
        this.applianceData = applianceData;
        utils = new Utils();
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ApplianceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appliance_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceAdapter.MyViewHolder holder, int position) {

        String name = applianceData.get(position).getApplianceName();
        String condition = applianceData.get(position).getApplianceCondition();
        String consumption = applianceData.get(position).getApplianceConsumption();
        String image = applianceData.get(position).getApplianceImage();

        holder.txtApplianceName.setText(name);
        holder.txtApplianceConsumption.setText(consumption);
        holder.txtApplianceCondition.setText(condition);

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                holder.applianceImage.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(holder.applianceImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return applianceData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtApplianceName, txtApplianceCondition, txtApplianceConsumption;
        ImageView applianceImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtApplianceName = itemView.findViewById(R.id.txt_appliance_name);
            txtApplianceCondition = itemView.findViewById(R.id.txt_condition_supplier);
            txtApplianceConsumption = itemView.findViewById(R.id.txt_appliance_consumption);

            applianceImage = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ViewApplianceActivity.class);
            i.putExtra(Constant.APPLIANCE_ID, applianceData.get(getAdapterPosition()).getApplianceId());
            context.startActivity(i);
        }
    }
}