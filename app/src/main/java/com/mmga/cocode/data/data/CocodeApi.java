package com.mmga.cocode.data.data;

import com.mmga.cocode.data.data.model.CocodeData;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface CocodeApi {

    String TAB_LATEST = "latest";
    String TAB_TOP = "top";

    String SERVICE_BASE_URL = "http://cocode.cc/";


    @GET("{tab}.json")
    Observable<CocodeData> getLatestData(@Path("tab") String tab,  @Query("page") int page);



}
