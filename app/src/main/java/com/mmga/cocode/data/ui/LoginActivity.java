package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mmga.cocode.R;
import com.mmga.cocode.data.base.BaseActivity;
import com.mmga.cocode.data.data.provider.LoginCallback;
import com.mmga.cocode.data.data.provider.LoginProvider;
import com.mmga.cocode.data.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginCallback{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_login)
    Button loginButton;
    @Bind(R.id.login_username)
    EditText editTextName;
    @Bind(R.id.login_password)
    EditText editTextPassword;

    LoginProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.login);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_left_white_24dp);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
        }
    }



    private void login() {
        String name = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();
        provider = new LoginProvider(this);
        provider.login(name, password);
    }

    @Override
    public void loginSucceed() {
        ToastUtil.showShort("登录成功");
    }

    @Override
    public void loginFailed(String errorText) {
        ToastUtil.showShort(errorText);
    }
}
