package com.electricity.monitoring.api;


import com.electricity.monitoring.Constant;
import com.electricity.monitoring.model.ClientNeighbourhood;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.Stage;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.model.Token;
import com.electricity.monitoring.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    //for login
    @FormUrlEncoded
    @POST("login")
    Call<User> login(
            @Field(Constant.KEY_METER_NUMBER) String meter_number,
            @Field(Constant.KEY_PASSWORD) String password);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(
            @Field(Constant.KEY_EMAIL) String email,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ADDRESS) String address,
            @Field(Constant.KEY_METER_NUMBER) String meter_number,
            @Field(Constant.KEY_FIRST_NAME) String first_name,
            @Field(Constant.KEY_LAST_NAME) String last_name,
            @Field(Constant.KEY_FCM_TOKEN) String fcm_token);

    @GET("neighbourhoods")
    Call<ArrayList<Neighbourhood>> getNeighbourhoods();

    @GET("clientneighbourhood")
    Call<ArrayList<Neighbourhood>> getClientNeighbourhood(
            @Query("client_id") String client_id
    );

    @GET("tarrif")
    Call<Tarrif> getTarrif();

    @FormUrlEncoded
    @POST("clientneighbourhood")
    Call<ClientNeighbourhood> clientneighbourhood(
            @Field("client_id") String client_id,
            @Field("neighbourhood_id") String neighbourhood_id
    );

    @GET("tokens")
    Call<ArrayList<Token>> getTokens(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("useToken")
    Call<ArrayList<Token>> useToken(
            @Field("token_id") String token_id
    );

    @FormUrlEncoded
    @POST("purchasetoken")
    Call<Token> purchasetoken(
            @Field("client_id") String client_id,
            @Field("amount_paid") String amount_paid);

    @GET("neighbourhoodstage")
    Call<Stage> getneighbourhoodstage(
            @Query("neighbourhood_id") String client_id
    );
}