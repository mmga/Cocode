package com.mmga.cocode.data.data.provider;


public interface LoginCallback {

    void loginSucceed();

    void loginFailed(String errorText);
}
