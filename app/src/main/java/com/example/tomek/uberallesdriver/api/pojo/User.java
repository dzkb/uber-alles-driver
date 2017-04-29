package com.example.tomek.uberallesdriver.api.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;

}