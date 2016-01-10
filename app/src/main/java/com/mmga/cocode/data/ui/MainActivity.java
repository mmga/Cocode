package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mmga.cocode.R;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.mmga.cocode.data.data.model.CocodeData;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    int page;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        final CocodeApi service = ServiceGenerator.createGetService(CocodeApi.class);


        setupTabView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.getLatestData(CocodeApi.TAB_LATEST,page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<CocodeData>() {
                            @Override
                            public void call(CocodeData c) {
                                Log.d("mmga", c.getTopicList().getTopics().get(1).getTitle());
                                Log.d("mmga", c.getTopicList().getTopics().get(2).getTitle());
                                Log.d("mmga", c.getTopicList().getTopics().get(3).getTitle());
                            }
                        });
            }
        });
    }

    private void setupTabView() {
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add(R.string.latest, LatestFragment.class)
                .add(R.string.top, TopFragment.class)
                .add(R.string.category, CategoryFragment.class)
                .create();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }



}
