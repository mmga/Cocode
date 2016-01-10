package com.mmga.cocode.data.data.provider;


import android.util.Log;

import com.mmga.cocode.data.data.AuthState;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.model.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataProvider {

    CocodeApi cocodeApi;
    List<Users> userList;
    LoadDataCallback callback;
    static String cookieForumSession;
    static String cookieT;
    boolean isLogin;


    public DataProvider(LoadDataCallback callback) {
        this.callback = callback;
        userList = new ArrayList<>();
    }


    public void loadData(int page) {
        if (!isLogin) {
            cocodeApi = ServiceGenerator.createGetService(CocodeApi.class);
        } else {
            String cookie = "_t=" + cookieT + "; " + "_forum_session=" + cookieForumSession;
            cocodeApi = ServiceGenerator.createGetService(CocodeApi.class, cookie);
        }
        Observable<CocodeData> observable = cocodeApi.getLatestData(CocodeApi.TAB_LATEST, page);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<CocodeData>() {
                    @Override
                    public void call(CocodeData cocodeData) {
                        userList = cocodeData.getUsers();
                    }
                })
                .map(new Func1<CocodeData, List<Topic>>() {
                    @Override
                    public List<Topic> call(CocodeData cocodeData) {
                        return cocodeData.getTopicList().getTopics();
                    }
                })
                .flatMap(new Func1<List<Topic>, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(List<Topic> topics) {
                        return Observable.from(topics);
                    }
                })
                .map(new Func1<Topic, Topic>() {
                    @Override
                    public Topic call(Topic topic) {
                        for (Users u : userList) {
                            if (u.getId() == topic.getPosters().get(0).getUserId()) {
                                topic.setAuthorName(u.getUserName());
                                topic.setAvatarUrl(u.getAvatar());
                                break;
                            }
                        }
                        return topic;
                    }
                })
                .toList()
                .subscribe(new Action1<List<Topic>>() {
                    @Override
                    public void call(List<Topic> topics) {
                        callback.OnLoadDataSuccess(topics);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.OnLoadDataError(throwable);
                    }
                });
    }

    public void login(final String loginName, final String loginPassword) {
        CocodeApi getTokenService = ServiceGenerator.createGetTokenService(CocodeApi.class);
        long timeStamp = new Date().getTime();

        getTokenService.getToken(timeStamp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<retrofit.Response<Token>>() {
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
//                        Log.d("mmga","getToken :"+ response.headers().toString());
                        String token = response.body().getToken();
                        cookieForumSession = header.substring(header.indexOf("=") + 1, header.indexOf(";"));
//                        Log.d("mmga", forumSession);
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
                        isLogin = true;
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
//                        Log.d("mmga", "_t = " + _t);
                        Log.d("mmga", "login onNext: " + response.body().getError());

                    }
                });
    }

}
