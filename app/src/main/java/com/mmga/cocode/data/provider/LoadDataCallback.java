package com.mmga.cocode.data.provider;

import com.mmga.cocode.data.model.Topic;

import java.util.List;


public interface LoadDataCallback {

    void OnLoadDataSuccess(List<Topic> list);

    void OnLoadDataComplete();

    void OnLoadDataError(Throwable e);


}
