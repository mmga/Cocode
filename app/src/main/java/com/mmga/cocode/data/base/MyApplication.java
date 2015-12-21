package com.mmga.cocode.data.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sContext = this;
    }
}
