package com.mmga.cocode.data.data.provider;


import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.model.Users;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DataProvider {

    CocodeApi cocodeApi;
    List<Users> userList;
    LoadDataCallback callback;
    String cookieForumSession;
    String cookieT;
    boolean isLogin;


    public DataProvider(LoadDataCallback callback) {
        this.callback = callback;
        userList = new ArrayList<>();
    }


    public void loadData(int page) {
//        if (!isLogin) {
        cocodeApi = ServiceGenerator.createGetService(CocodeApi.class);
//        } else {
//            String cookie = "_t=" + cookieT + "; " + "_forum_session=" + cookieForumSession;
//            cocodeApi = ServiceGenerator.createGetService(CocodeApi.class, cookie);
//        }
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


}
