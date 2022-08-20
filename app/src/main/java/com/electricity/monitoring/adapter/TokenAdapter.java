package com.electricity.monitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electricity.monitoring.R;
import com.electricity.monitoring.model.Token;
import com.electricity.monitoring.tokens.ViewTokenActivity;
import com.electricity.monitoring.utils.Utils;

import java.util.ArrayList;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.MyViewHolder> {
    private final ArrayList<Token> tokenData;
    private final Context context;
    Utils utils;

    public TokenAdapter(ArrayList<Token> tokenData, Context context) {
        this.tokenData = tokenData;
        this.context = context;
    }

    @NonNull
    @Override
    public TokenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenAdapter.MyViewHolder holder, int position) {

        Token token = tokenData.get(position);

        String id = token.getId();
        String client = token.getClient();
        String amount_paid = token.getAmount_paid();
        String token_number = token.getToken_number();
        String power_bought = token.getPower_bought();

        holder.txt_id.setText("Token Id : " + id);
        holder.txt_amount.setText("Amount paid : $" + amount_paid);
        holder.txt_power.setText("Power bought : " + power_bought + " KWh");
    }

    @Override
    public int getItemCount() {
        return tokenData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_amount, txt_power, txt_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_power = itemView.findViewById(R.id.txt_power);
            txt_id = itemView.findViewById(R.id.txt_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ViewTokenActivity.class);
            i.putExtra("TOKEN_ID", tokenData.get(getAdapterPosition()).getId());
            i.putExtra("AMOUNT_PAID", tokenData.get(getAdapterPosition()).getAmount_paid());
            i.putExtra("POWER_BOUGHT", tokenData.get(getAdapterPosition()).getPower_bought());
            i.putExtra("TOKEN_NUMBER", tokenData.get(getAdapterPosition()).getToken_number());
            i.putExtra("CLIENT", tokenData.get(getAdapterPosition()).getClient());
            i.putExtra("ADDRESS", tokenData.get(getAdapterPosition()).getAddress());
            i.putExtra("METER_NUMBER", tokenData.get(getAdapterPosition()).getMeter_number());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }
}
