package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mmga.cocode.R;
import com.mmga.cocode.data.base.MyApplication;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.MmgaFactory;
import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.model.Users;
import com.mmga.cocode.data.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TopFragment extends Fragment {

    CocodeApi cocodeApi;
    Subscription subscription;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    List<Users> userList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.latest_fragment, container, false);

        mLayoutManager = new LinearLayoutManager(MyApplication.sContext);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.latest_recyclerview);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        cocodeApi = MmgaFactory.createGetService(CocodeApi.class);


        subscription = cocodeApi.getLatestData(CocodeApi.TAB_TOP, 0)
                .subscribeOn(Schedulers.newThread())
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
//                        Log.d("mmga", "map");
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
//                        Log.d("mmga", topic.getTitle());
                        for (Users u : userList) {
                            if (u.getId() == topic.getPosters().get(0).getUserId()) {
                                topic.setAuthorName(u.getUserName());
                                topic.setAvatarUrl(u.getAvatar());
                                break;
                            }
                        }
//                        Log.d("mmga", "avatar = " + topic.getAvatarUrl());
                        return topic;
                    }
                })
                .toList()
                .subscribe(subscriber);


    }


    Subscriber<List<Topic>> subscriber = new Subscriber<List<Topic>>() {
        @Override
        public void onCompleted() {
            Toast.makeText(MyApplication.sContext, "complete", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(MyApplication.sContext, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Topic> topics) {
            mAdapter.setAdapterData(topics);
        }


    };



}
