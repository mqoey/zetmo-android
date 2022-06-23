package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("meter_number")
    private String meter_number;

    @SerializedName("password")
    private String password;

    public Login(String meter_number, String password) {
        this.meter_number = meter_number;
        this.password = password;
    }

    public String getMeter_number() {
        return meter_number;
    }

    public void setMeter_number(String meter_number) {
        this.meter_number = meter_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
