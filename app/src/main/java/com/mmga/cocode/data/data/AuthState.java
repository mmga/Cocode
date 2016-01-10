package com.mmga.cocode.data.data;

import com.google.gson.annotations.Expose;

/**
 * Created by mmga on 2016/1/10.
 */
public class AuthState {

    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
