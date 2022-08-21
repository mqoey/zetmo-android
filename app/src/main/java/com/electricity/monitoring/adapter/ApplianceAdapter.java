package com.electricity.monitoring.adapter;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import com.electricity.monitoring.MyBroadcastReceiver;
import com.electricity.monitoring.R;
import com.electricity.monitoring.appliance.ViewApplianceActivity;
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
        holder.txtApplianceConsumption.setText(consumption + " KWh per hour");
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

                    Date endTime = null, startTime = null;
                    ArrayList<ApplianceTime> applianceTimeData = dbHandler.getAppliancesByID2(applianceID);
                    String start_time = applianceTimeData.get(0).getStart_time();
                    String end_time = applianceTimeData.get(0).getEnd_time();

                    try {
                        startTime = mdformat.parse(start_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (end_time.equals("pending")) {
                            Calendar calendar = Calendar.getInstance();
                            String time = mdformat.format(calendar.getTime());
                            endTime = mdformat.parse(time);
                        } else {
                            endTime = mdformat.parse(end_time);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long difference = endTime.getTime() - startTime.getTime();

                    if (difference < 0) {
                        Date dateMax = null;
                        try {
                            dateMax = mdformat.parse("24:00:00");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date dateMin = null;
                        try {
                            dateMin = mdformat.parse("00:00:00");
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

                    dbHandler.stopApplianceTimer(applianceID, date, String.valueOf(consumption), duration);
                    sp.edit().putBoolean("isChecked" + applianceID, false).apply();

//                    try try try

                    String threshold = dbHandler.checkThreshold();
                    String alarm = dbHandler.checkAlarm();

                    if (Double.parseDouble(threshold) < Double.parseDouble(alarm)) {
                        Intent intent = new Intent(context, MyBroadcastReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                context, 0, intent, PendingIntent.FLAG_IMMUTABLE
                        );
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                        dbHandler.checkAlarm();
                    }

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