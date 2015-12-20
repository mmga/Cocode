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
import com.mmga.cocode.data.ui.adapter.RecyclerViewAdapter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LatestFragment extends Fragment {

    List<CocodeData> list;
    CocodeApi cocodeApi;
    Subscription subscription;
    RecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.latest_fragment, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.sContext);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.latest_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



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
                Log.d("mmga", "" + topics.size());
                mAdapter.setRecyclerViewAdapterData(topics);
            }


        };
        cocodeApi = MmgaFactory.createRetrofitService(CocodeApi.class);
        subscription = cocodeApi.getCocodeData(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CocodeData, List<Topic>>() {
                    @Override
                    public List<Topic> call(CocodeData cocodeData) {
                        return cocodeData.getTopicList().getTopics();
                    }
                })
                .subscribe(subscriber);



    }
}
