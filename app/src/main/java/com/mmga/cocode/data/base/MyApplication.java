package com.mmga.cocode.data.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mmga.cocode.Constant;
import com.mmga.cocode.data.data.provider.Cookie;
import com.mmga.cocode.data.util.SharedPrefsUtil;


public class MyApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sContext = this;
        Cookie.setCookieT(SharedPrefsUtil.getValue(this, Constant.COOKIE_T, ""));
    }

}
