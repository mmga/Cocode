package com.mmga.cocode.data.data.provider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Token {

    @Expose
    @SerializedName("csrf")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
