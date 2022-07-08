package com.electricity.monitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.R;
import com.electricity.monitoring.model.ApplianceTime;
import com.electricity.monitoring.report.ViewReportActivity;
import com.electricity.monitoring.utils.Utils;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private final ArrayList<ApplianceTime> applianceTimeData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;

    public ReportAdapter(Context context, ArrayList<ApplianceTime> applianceTimeData) {
        this.context = context;
        this.applianceTimeData = applianceTimeData;
        utils = new Utils();
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appliancetime_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.MyViewHolder holder, int position) {

        String date = applianceTimeData.get(position).getDate();

        holder.txtDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return applianceTimeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txt_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ViewReportActivity.class);
            i.putExtra(Constant.DATE, applianceTimeData.get(getAdapterPosition()).getDate());
            context.startActivity(i);
        }
    }
}