package com.electricity.monitoring.model;

import com.google.gson.annotations.SerializedName;

public class ClientNeighbourhood {
    @SerializedName("id")
    private String id;

    @SerializedName("client_id")
    private String client_id;

    @SerializedName("neighbourhood_id")
    private String neighbourhood_id;

    public ClientNeighbourhood(String id, String client_id, String neighbourhood_id) {
        this.id = id;
        this.client_id = client_id;
        this.neighbourhood_id = neighbourhood_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getNeighbourhood_id() {
        return neighbourhood_id;
    }

    public void setNeighbourhood_id(String neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
    }
}
