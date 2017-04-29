package com.example.tomek.uberallesdriver.api.pojo;

import com.google.gson.annotations.SerializedName;


public class CreateAccount {

    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("password")
    public String password;
    @SerializedName("registrationToken")
    public String token;
    @SerializedName("role")
    public String role;

    public CreateAccount(String phoneNumber, String firstName, String lastName, String password, String token) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.token = token;
        this.role = "customer";
    }
}