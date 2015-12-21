package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LatestFragment extends Fragment {

    CocodeApi cocodeApi;
    Subscription subscription;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    List<Users> userList = new ArrayList<>();
    @Bind(R.id.ptr_framelayout)
    PtrFrameLayout ptrFrameLayout;
    int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.latest_fragment, container, false);

        mLayoutManager = new LinearLayoutManager(MyApplication.sContext);
        final RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.latest_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (lastVisibleItem == mAdapter.getItemCount() - 1) {
                    Log.d("mmga", "itemCount= "+ mAdapter.getItemCount());
//                    page++;
////                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configPTR();
        cocodeApi = MmgaFactory.createRetrofitService(CocodeApi.class);
        loadData();

    }






    private void loadData() {

        subscription = cocodeApi.getLatestData(CocodeApi.TAB_LATEST, page)
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
            Log.d("mmga", "onNext");
            mAdapter.addAdapterData(topics);
        }


    };

    //下拉刷新
    private void configPTR() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(MyApplication.sContext);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //置零页数计数
                page = 0;
                loadData();
                frame.refreshComplete();
            }
        });
    }



}
