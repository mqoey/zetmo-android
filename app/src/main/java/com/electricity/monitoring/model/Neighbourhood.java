package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class Neighbourhood {
    @SerializedName("id")
    private String id;

    @SerializedName("area")
    private String area;

    @SerializedName("name")
    private String name;

    @SerializedName("municipality")
    private String municipality;

    public Neighbourhood(String id, String area, String name, String municipality) {
        this.id = id;
        this.area = area;
        this.name = name;
        this.municipality = municipality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
