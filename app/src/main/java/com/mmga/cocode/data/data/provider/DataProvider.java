package com.mmga.cocode.data.data.provider;


import android.util.Log;

import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.MmgaFactory;
import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.model.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public DataProvider(LoadDataCallback callback) {
        this.callback = callback;
        userList = new ArrayList<>();
    }


    public void loadData(int page) {
        cocodeApi = MmgaFactory.createGetService(CocodeApi.class);
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
        CocodeApi getTokenService = MmgaFactory.createGetService(CocodeApi.class);
        long timeStamp = new Date().getTime();

        getTokenService.getToken(timeStamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Token>() {
                    @Override
                    public void call(Token token) {
                        Log.d("mmga", token.getToken());
                        loginWithToken(token.getToken(), loginName, loginPassword);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("mmga", "getToken error " + throwable.getMessage());
                    }
                });


    }

    private void loginWithToken(String token, String loginName, String loginPassword) {
        CocodeApi loginWithTokenService = MmgaFactory.createLoginService(CocodeApi.class, token);

        loginWithTokenService.login(loginName, loginPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("mmga", "login completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("mmga", "login error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d("mmga", "login onNext");
                    }
                });
    }

}
