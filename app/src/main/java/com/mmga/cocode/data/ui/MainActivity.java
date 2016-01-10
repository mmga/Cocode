package com.mmga.cocode.data.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mmga.cocode.R;
import com.mmga.cocode.data.data.CocodeApi;
import com.mmga.cocode.data.data.ServiceGenerator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @Bind(R.id.fab)
//    FloatingActionButton fab;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;
    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        final CocodeApi service = ServiceGenerator.createGetService(CocodeApi.class);


        setupTabView();

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
