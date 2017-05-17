package com.example.szymon.app.api.pojo;

import com.google.gson.annotations.SerializedName;

public class RegistrationToken {

    public RegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    @SerializedName("registrationToken")
    public String registrationToken;
}
