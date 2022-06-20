package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Registration {
    @SerializedName("firstname")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("meter_number")
    private String meter_number;

    @SerializedName("password")
    private String password;

    public Registration(String first_name, String last_name, String email, String address, String meter_number, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.meter_number = meter_number;
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
