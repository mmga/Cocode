package com.mmga.cocode.data.data.provider;

import android.util.Log;

import com.mmga.cocode.R;
import com.mmga.cocode.data.base.MyApplication;
import com.mmga.cocode.data.data.AuthState;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;

import java.util.Date;
import java.util.List;

import retrofit.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginProvider {

    LoginCallback callback;

    String cookieForumSession;
    String cookieT;

    public LoginProvider(LoginCallback callback) {
        this.callback = callback;
    }

    public void login(final String loginName, final String loginPassword) {
        CocodeApi getTokenService = ServiceGenerator.createGetTokenService(CocodeApi.class);
        long timeStamp = new Date().getTime();

        getTokenService.getToken(timeStamp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Token>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.loginFailed(MyApplication.sContext.getString(R.string.login_timeout));
                        Log.d("mmga", "getToken error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(retrofit.Response<Token> response) {
                        String header = response.headers().get("Set-Cookie");
                        Log.d("mmga", "getToken :" + response.headers().toString());
                        String token = response.body().getToken();
                        cookieForumSession = header.substring(header.indexOf("=") + 1, header.indexOf(";"));
                        Cookie.setForumSession(cookieForumSession);
//                        Log.d("mmga", cookieForumSession);
                        String cookie = Cookie.getCookie();
                        loginWithToken(token, cookie, loginName, loginPassword);
                    }
                });


    }

    private void loginWithToken(String token, String cookie, String loginName, String loginPassword) {
        CocodeApi loginWithTokenService = ServiceGenerator.createLoginService(CocodeApi.class, token, cookie);

        loginWithTokenService.login(loginName, loginPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<AuthState>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.loginFailed(MyApplication.sContext.getString(R.string.password_null));
                        Log.d("mmga", "login error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Response<AuthState> response) {
                        List<String> cookies = response.headers().values("Set-Cookie");
                        String cookie = cookies.get(0);

//                        Log.d("mmga", "Login :" + response.headers().toString());
                        if (cookie.startsWith("_t")) {
                            cookieT = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
                            Cookie.setCookieT(cookieT);
                            Log.d("mmga", "_t = " + cookieT);
                            callback.loginSucceed();
                        } else {
                            callback.loginFailed(response.body().getError());
                        }

                        Log.d("mmga", "login onNext: " + response.raw().message());

                    }
                });
    }
}
