package com.mmga.cocode.data.data.provider;

import com.mmga.cocode.data.data.model.Topic;

import java.util.List;

/**
 * Created by mmga on 2016/1/2.
 */
public interface LoadDataCallback {

    void OnLoadDataSuccess(List<Topic> list);

    void OnLoadDataComplete();

    void OnLoadDataError(Throwable e);


}
