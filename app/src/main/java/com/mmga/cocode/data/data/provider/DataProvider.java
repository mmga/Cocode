package com.mmga.cocode.data.data.provider;


import android.util.Log;

import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.model.UserProfile;
import com.mmga.cocode.data.data.model.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataProvider {

    CocodeApi cocodeApi;
    List<Users> userList;
    LoadDataCallback callback;
    String tabName;
    String cookieForumSession;
    String cookieT;
    boolean isLogin;


    public DataProvider(String tabName,LoadDataCallback callback) {
        this.tabName = tabName;
        this.callback = callback;
        userList = new ArrayList<>();
        cocodeApi = ServiceGenerator.createCocodeService(CocodeApi.class);
    }


    public void loadData(int page) {
        Log.d("mmga", "dataProvider : cookie = " + Cookie.getCookie());
        Observable<Response<CocodeData>> observable = cocodeApi.getLatestData(Cookie.getCookie(),tabName, page);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<CocodeData>, Observable<CocodeData>>() {
                    @Override
                    public Observable<CocodeData> call(Response<CocodeData> cocodeDataResponse) {
//                        String cookie = cocodeDataResponse.headers().get("Set-Cookie");
//                        String forumSession = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
//                        Cookie.setForumSession(forumSession);
                        return Observable.just(cocodeDataResponse.body());
                    }
                })
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

    public void getUserProfile(String userName) {
        Observable<UserProfile> observable = cocodeApi.getUserProfile(Cookie.getCookie(), userName);
        observable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserProfile>() {
                    @Override
                    public void call(UserProfile userProfile) {
                        userProfile.getUsers().toString();
                    }
                });


    }


}
