package com.example.szymon.app.api.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("role")
    public String role;
}