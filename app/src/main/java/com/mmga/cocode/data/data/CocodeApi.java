package com.mmga.cocode.data.data;

import com.mmga.cocode.data.data.model.CocodeData;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


public interface CocodeApi {

    String SERVICE_BASE_URL = "http://cocode.cc/";


    @GET("latest.json")
    Observable<CocodeData> getCocodeData(@Query("page") int page);
}
