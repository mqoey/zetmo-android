package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Appliance {

    @SerializedName("appliance_id")
    private String applianceId;

    @SerializedName("appliance_name")
    private String applianceName;

    @SerializedName("appliance_description")
    private String applianceDescription;

    @SerializedName("appliance_condition")
    private String applianceCondition;

    @SerializedName("appliance_years")
    private String applianceYears;

    @SerializedName("appliance_consumption")
    private String applianceConsumption;

    @SerializedName("appliance_image")
    private String applianceImage;

    public Appliance(String applianceId, String applianceName, String applianceDescription, String applianceCondition, String applianceYears, String applianceConsumption, String applianceImage) {
        this.applianceId = applianceId;
        this.applianceName = applianceName;
        this.applianceDescription = applianceDescription;
        this.applianceCondition = applianceCondition;
        this.applianceYears = applianceYears;
        this.applianceConsumption = applianceConsumption;
        this.applianceImage = applianceImage;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getApplianceDescription() {
        return applianceDescription;
    }

    public void setApplianceDescription(String applianceDescription) {
        this.applianceDescription = applianceDescription;
    }

    public String getApplianceCondition() {
        return applianceCondition;
    }

    public void setApplianceCondition(String applianceCondition) {
        this.applianceCondition = applianceCondition;
    }

    public String getApplianceYears() {
        return applianceYears;
    }

    public void setApplianceYears(String applianceYears) {
        this.applianceYears = applianceYears;
    }

    public String getApplianceConsumption() {
        return applianceConsumption;
    }

    public void setApplianceConsumption(String applianceConsumption) {
        this.applianceConsumption = applianceConsumption;
    }

    public String getApplianceImage() {
        return applianceImage;
    }

    public void setApplianceImage(String applianceImage) {
        this.applianceImage = applianceImage;
    }
}
