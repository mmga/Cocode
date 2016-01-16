package com.mmga.cocode.data.data.provider;

import android.util.Log;

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

    String cookieForumSession;
    String cookieT;
    boolean isLogin;


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
                        Log.d("mmga", e.getMessage());
                    }

                    @Override
                    public void onNext(retrofit.Response<Token> response) {
                        String header = response.headers().get("Set-Cookie");
                        Log.d("mmga","getToken :"+ response.headers().toString());
                        String token = response.body().getToken();
                        cookieForumSession = header.substring(header.indexOf("=") + 1, header.indexOf(";"));
                        Log.d("mmga", cookieForumSession);
                        String cookie = "_t=" + cookieT + "; " + "_forum_session=" + cookieForumSession;
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
//                        isLogin = true;
                        Log.d("mmga", "login completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("mmga", "login error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Response<AuthState> response) {
                        List<String> cookies = response.headers().values("Set-Cookie");
                        String cookie = cookies.get(0);
                        cookieT = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
//                        Log.d("mmga", "Login :" + response.headers().toString());
                        Log.d("mmga", "_t = " + cookieT);
                        Log.d("mmga", "login onNext: " + response.body().getError());

                    }
                });
    }
}
