package com.electricity.monitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.appliance.ViewApplianceActivity;
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.utils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.MyViewHolder> {

    private final ArrayList<Appliance> applianceData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;
    DBHandler dbHandler;

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
        String applianceID = applianceData.get(position).getApplianceId();

        holder.txtApplianceName.setText(name);
        holder.txtApplianceConsumption.setText(consumption + " KHz per hour");
        holder.txtApplianceCondition.setText(condition);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String time = mdformat.format(calendar.getTime());

        boolean value = false;

        value = sp.getBoolean("isChecked" + applianceID, value); // retrieve the value of your key
        holder.appliance_switch.setChecked(value);

        holder.appliance_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbHandler = new DBHandler(context);
                    dbHandler.startApplianceTimer(applianceID, date, time);
                    sp.edit().putBoolean("isChecked" + applianceID, true).apply();
                } else {
                    dbHandler = new DBHandler(context);
                    dbHandler.stopApplianceTimer(applianceID, date);
                    sp.edit().putBoolean("isChecked" + applianceID, false).apply();
                }
            }
        });

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
        Switch appliance_switch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtApplianceName = itemView.findViewById(R.id.txt_appliance_name);
            txtApplianceCondition = itemView.findViewById(R.id.txt_condition_supplier);
            txtApplianceConsumption = itemView.findViewById(R.id.txt_appliance_consumption);
            applianceImage = itemView.findViewById(R.id.product_image);
            appliance_switch = itemView.findViewById(R.id.switch_appliance);

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