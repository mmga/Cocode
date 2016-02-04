package com.mmga.cocode.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mmga.cocode.Constant;
import com.mmga.cocode.R;
import com.mmga.cocode.base.MyApplication;
import com.mmga.cocode.data.CocodeApi;
import com.mmga.cocode.data.ServiceGenerator;
import com.mmga.cocode.data.model.Topic;
import com.mmga.cocode.data.model.Users;
import com.mmga.cocode.data.provider.DataProvider;
import com.mmga.cocode.data.provider.LoadDataCallback;
import com.mmga.cocode.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

public class TopFragment extends Fragment implements LoadDataCallback {

    CocodeApi cocodeApi;
    Subscription subscription;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    List<Users> userList = new ArrayList<>();
    DataProvider provider;
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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.latest_recyclerview);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        provider = new DataProvider(Constant.TAB_TOP, this);
        cocodeApi = ServiceGenerator.createCocodeService(CocodeApi.class);
        provider.loadData(page);

    }


    @Override
    public void OnLoadDataSuccess(List<Topic> list) {
        mAdapter.setAdapterData(list);

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
