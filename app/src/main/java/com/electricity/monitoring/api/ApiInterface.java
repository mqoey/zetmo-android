package com.electricity.monitoring.api;


import com.electricity.monitoring.Constant;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.Registration;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //for login
    @FormUrlEncoded
    @POST("login")
    Call<User> login(
            @Field(Constant.KEY_METER_NUMBER) String meter_number,
            @Field(Constant.KEY_PASSWORD) String password);

    @FormUrlEncoded
    @POST("register")
    Call<Registration> register(
            @Field(Constant.KEY_EMAIL) String email,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_METER_NUMBER) String meter_number,
            @Field(Constant.KEY_FIRST_NAME) String first_name,
            @Field(Constant.KEY_LAST_NAME) String last_name);

    @GET("neighbourhoods")
    Call<ArrayList<Neighbourhood>> getNeighbourhoods();

    @GET("tarrif")
    Call<Tarrif> getTarrif();
}