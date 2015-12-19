package com.mmga.cocode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mmga.cocode.data.CocodeApi;
import com.mmga.cocode.data.MmgaFactory;
import com.mmga.cocode.data.model.CocodeData;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        final CocodeApi service = MmgaFactory.createRetrofitService(CocodeApi.class);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.getCocodeData(page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<CocodeData>() {
                            @Override
                            public void call(CocodeData c) {
                                Log.d("mmga", "" + c.getTopicList().getTopics().get(2).getCreatedAt());
                                Log.d("mmga", c.getTopicList().getTopics().get(1).getTitle());
                            }
                        });
            }
        });
    }


}
