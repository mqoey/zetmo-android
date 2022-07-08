package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class ApplianceTime {
    @SerializedName("appliance_id")
    private String applianceId;

    @SerializedName("start_time")
    private String start_time;

    @SerializedName("end_time")
    private String end_time;

    @SerializedName("date")
    private String date;

    @SerializedName("duration")
    private String duration;

    @SerializedName("consumption")
    private String consumption;


    public ApplianceTime(String applianceId, String start_time, String end_time, String date, String duration, String consumption) {
        this.applianceId = applianceId;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
        this.duration = duration;
        this.consumption = consumption;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
}
