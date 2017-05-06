package com.example.szymon.app.api;

import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Localisation;
import com.example.szymon.app.api.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserService {

    @GET("/users")
    Call<User> basicLogin();
    @POST("/acceptedFares/{fareId}")
    Call<String> acceptFare(@Path("fareId") String fareId);
    @DELETE("/acceptedFares/{fareId}")
    Call<String> cancelFare(@Path("fareId") String fareId);
    @GET("/fares")
    Call<List<Fare>> allFares();
    @GET("/acceptedFares")
    Call<List<Fare>> acceptedFares();
    @POST("/completedFares/{fareId}")
    Call<String> completeFare(@Path("fareId") String fareId);
    @PUT("/localisation")
    Call<Localisation> putLocalisation(@Body Localisation currentLocalisation);
}