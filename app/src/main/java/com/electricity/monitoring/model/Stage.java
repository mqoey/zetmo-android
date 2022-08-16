package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Stage {

    @SerializedName("id")
    private String id;

    @SerializedName("stage_id")
    private String stage_id;

    @SerializedName("day")
    private String day;

    @SerializedName("off")
    private String off;

    @SerializedName("on")
    private String on;

    public Stage(String id, String stage_id, String day, String off, String on) {
        this.id = id;
        this.stage_id = stage_id;
        this.day = day;
        this.off = off;
        this.on = on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStage_id() {
        return stage_id;
    }

    public void setStage_id(String stage_id) {
        this.stage_id = stage_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }
}
