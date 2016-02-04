package com.mmga.cocode.data.provider;


public interface LoginCallback {

    void loginSucceed();

    void loginFailed(String errorText);
}
