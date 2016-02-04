package com.mmga.cocode.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mmga.cocode.util.ToastUtil;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtil.register();
    }
}
