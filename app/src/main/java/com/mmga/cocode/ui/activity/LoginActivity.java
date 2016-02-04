package com.mmga.cocode.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mmga.cocode.Constant;
import com.mmga.cocode.R;
import com.mmga.cocode.base.BaseActivity;
import com.mmga.cocode.data.provider.LoginCallback;
import com.mmga.cocode.data.provider.LoginProvider;
import com.mmga.cocode.util.SharedPrefsUtil;
import com.mmga.cocode.util.StatusBarCompat;
import com.mmga.cocode.util.ToastUtil;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginCallback,Validator.ValidationListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_login)
    Button loginButton;
    @Bind(R.id.login_username)
    @NotEmpty
    @Email
    EditText editTextName;
    @Bind(R.id.login_password)
    @Password(min = 6, scheme = Password.Scheme.ANY)
    EditText editTextPassword;

    LoginProvider provider;
    com.mobsandgeeks.saripaar.Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));

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
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    login();
                    return true;
                }
                return false;
            }
        });

        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                validator.validate();
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
        ToastUtil.showShort(getString(R.string.login_succeed));
        SharedPrefsUtil.putValue(this, Constant.COOKIE_T, "");
        finish();
    }

    @Override
    public void loginFailed(String errorText) {
        ToastUtil.showShort(errorText);
    }

    @Override
    public void onValidationSucceeded() {
        login();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
