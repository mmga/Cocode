package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mmga.cocode.R;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.mmga.cocode.data.ui.adapter.MyPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        final CocodeApi service = ServiceGenerator.createGetService(CocodeApi.class);


        setupViewPager();
//        setupTabView();

    }

    private void setupViewPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LatestFragment(), "最新");
        adapter.addFragment(new TopFragment(), "热门");
        adapter.addFragment(new CategoryFragment(), "分类");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
