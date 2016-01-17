package com.mmga.cocode.data.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mmga.cocode.data.util.ToastUtil;


public class MyApplication extends Application {

    public static Context sContext;

//    private String cookieT;
//
//    public String getCookieT() {
//        return cookieT;
//    }
//
//    public void setCookieT(String cookieT) {
//        this.cookieT = cookieT;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sContext = this;
    }
}
