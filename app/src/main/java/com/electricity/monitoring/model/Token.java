package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("id")
    private String id;

    @SerializedName("client")
    private String client;

    @SerializedName("token_number")
    private String token_number;

    @SerializedName("amount_paid")
    private String amount_paid;

    @SerializedName("power_bought")
    private String power_bought;

    @SerializedName("meter_number")
    private String meter_number;

    @SerializedName("address")
    private String address;


    public Token(String id, String client, String token_number, String amount_paid, String power_bought, String meter_number, String address) {
        this.id = id;
        this.client = client;
        this.token_number = token_number;
        this.amount_paid = amount_paid;
        this.power_bought = power_bought;
        this.meter_number = meter_number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getToken_number() {
        return token_number;
    }

    public void setToken_number(String token_number) {
        this.token_number = token_number;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getPower_bought() {
        return power_bought;
    }

    public void setPower_bought(String power_bought) {
        this.power_bought = power_bought;
    }

    public String getMeter_number() {
        return meter_number;
    }

    public void setMeter_number(String meter_number) {
        this.meter_number = meter_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
