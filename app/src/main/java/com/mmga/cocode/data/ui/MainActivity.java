package com.mmga.cocode.data.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmga.cocode.R;
import com.mmga.cocode.data.base.BaseActivity;
import com.mmga.cocode.data.ui.adapter.MyPagerAdapter;
import com.mmga.cocode.data.util.StatusBarCompat;
import com.mmga.cocode.data.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.navigation_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        ButterKnife.bind(this);


//        LoadUserProfile();
        setupViewPager();
        setupDrawer();

    }

//    private void LoadUserProfile() {
//        CocodeApi service = ServiceGenerator.createCocodeService(CocodeApi.class);
////        service.getUserProfile()
//    }


    private void setupViewPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LatestFragment(), "最新");
        adapter.addFragment(new TopFragment(), "热门");
        adapter.addFragment(new CategoryFragment(), "分类");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupDrawer() {
        TextView loginText = (TextView) navView.getHeaderView(0).findViewById(R.id.login);
        SimpleDraweeView userAvatar = (SimpleDraweeView) navView.getHeaderView(0).findViewById(R.id.my_avatar);
        // TODO: 2016/1/24 用户头像
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_messages:
                                ToastUtil.showShort(".");
                                break;
                            case R.id.nav_notification:
                                ToastUtil.showShort(".");
                                break;
                            case R.id.nav_friends:
                                ToastUtil.showShort(".");
                                break;
                        }
                        return true;
                    }
                }
        );
    }

}
