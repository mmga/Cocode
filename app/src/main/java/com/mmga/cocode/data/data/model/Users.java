package com.mmga.cocode.data.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @Expose
    private int id;

    @Expose
    @SerializedName("username")
    String userName;

    @Expose
    @SerializedName("avatar_template")
    String avatar;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
