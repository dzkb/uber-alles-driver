package com.example.tomek.uberallesdriver.api;

import com.example.tomek.uberallesdriver.api.pojo.CreateAccount;
import com.example.tomek.uberallesdriver.api.pojo.Fare;
import com.example.tomek.uberallesdriver.api.pojo.FareProof;
import com.example.tomek.uberallesdriver.api.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {

    @GET("/users")
    Call<User> basicLogin();
    @POST("/users")
    Call<User> createAccount(@Body CreateAccount account);
    @POST("/fares")
    Call<FareProof> createFare(@Body Fare fare);
    @DELETE("/fares/{fareId}")
    Call<Fare> deleteFare(@Path("fareId") String fareId);
}