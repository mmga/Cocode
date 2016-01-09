package com.mmga.cocode.data.data;

import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.provider.Token;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface CocodeApi {

    String TAB_LATEST = "latest";
    String TAB_TOP = "top";

    String SERVICE_BASE_URL = "http://cocode.cc/";


    @GET("{tab}.json")
    Observable<CocodeData> getLatestData(@Path("tab") String tab, @Query("page") int page);

    @FormUrlEncoded
    @POST("session.json")
    Observable<String> login(@Field("username") String loginName,
                             @Field("password") String loginPassword);

    @GET("session/csrf.json")
    Observable<Token> getToken(@Query("_") long timeStamp);

}
