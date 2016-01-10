package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.mmga.cocode.data.data.model.Topic;
import com.mmga.cocode.data.data.provider.DataProvider;
import com.mmga.cocode.data.data.provider.LoadDataCallback;
import com.mmga.cocode.data.ui.adapter.RecyclerViewAdapter;

import java.util.List;

import butterknife.ButterKnife;


public class LatestFragment extends Fragment implements LoadDataCallback {


    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    int page = 0;
    DataProvider provider;

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
                    Log.d("mmga", "itemCount= " + mAdapter.getItemCount());
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
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        provider = new DataProvider(this);
        provider.loadData(page);
    }

    private void Login() {
        provider.login("glueMe@163.com","234186196");
    }


    @Override
    public void OnLoadDataSuccess(List<Topic> topics) {
        Log.d("mmga", "onNext");
        mAdapter.addAdapterData(topics);
    }

    @Override
    public void OnLoadDataComplete() {
        Toast.makeText(MyApplication.sContext, "complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoadDataError(Throwable e) {
        Toast.makeText(MyApplication.sContext, "error", Toast.LENGTH_SHORT).show();
    }
}
