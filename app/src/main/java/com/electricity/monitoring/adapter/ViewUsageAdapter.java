package com.electricity.monitoring.adapter;

import android.content.Context;
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
import com.electricity.monitoring.database.DBHandler;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.model.ApplianceTime;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.utils.Utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewUsageAdapter extends RecyclerView.Adapter<ViewUsageAdapter.MyViewHolder> {

    private final ArrayList<ApplianceTime> applianceTimeData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;

    public ViewUsageAdapter(Context context, ArrayList<ApplianceTime> applianceTimeData) {
        this.context = context;
        this.applianceTimeData = applianceTimeData;
        utils = new Utils();
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewUsageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appliance_report_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUsageAdapter.MyViewHolder holder, int position) {

        Date endTime = null, startTime = null;
        String date = applianceTimeData.get(position).getDate();
        String applianceID = applianceTimeData.get(position).getApplianceId();
        String start_time = applianceTimeData.get(position).getStart_time();
        String end_time = applianceTimeData.get(position).getEnd_time();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            startTime = simpleDateFormat.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (end_time.equals("pending")) {
                Calendar calendar = Calendar.getInstance();
               String time = simpleDateFormat.format(calendar.getTime());
                endTime = simpleDateFormat.parse(time);
            }else {
                endTime = simpleDateFormat.parse(end_time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endTime.getTime() - startTime.getTime();

        if (difference < 0) {
            Date dateMax = null;
            try {
                dateMax = simpleDateFormat.parse("24:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateMin = null;
            try {
                dateMin = simpleDateFormat.parse("00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            difference = (dateMax.getTime() - startTime.getTime()) + (endTime.getTime() - dateMin.getTime());
        }
        int hours = (int) (difference / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * hours)) / (1000 * 60);
        DBHandler dbHandler = new DBHandler(context);

        ArrayList<Tarrif> tarrifArrayList;
        tarrifArrayList = dbHandler.getTarrif();
        String tarrif = tarrifArrayList.get(0).getPrice();

        ArrayList<Appliance> appliance = dbHandler.getAppliancesByID(applianceID);

        String applianceConsumption = appliance.get(0).getApplianceConsumption();

        String duration = hours + "hrs " + min + "mins";
        double consumption = (hours * Double.parseDouble(applianceConsumption)) + (min * (Double.parseDouble(applianceConsumption)) / 60);

        String image = appliance.get(0).getApplianceImage();

        holder.txtName.setText(appliance.get(0).getApplianceName());
        holder.txtDuration.setText(duration);
        holder.txt_consumption.setText(new StringBuilder().append(consumption).append(" KWh").toString());

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
        return applianceTimeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtDuration, txtName, txt_consumption;
        ImageView applianceImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDuration = itemView.findViewById(R.id.txt_duration);
            txtName = itemView.findViewById(R.id.txt_name);
            txt_consumption = itemView.findViewById(R.id.txt_consumption);
            applianceImage = itemView.findViewById(R.id.report_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}