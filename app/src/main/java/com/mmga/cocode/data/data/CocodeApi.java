package com.mmga.cocode.data.data;

import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.provider.Token;

import retrofit.Response;
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
    @POST("session")
    Observable<Response<AuthState>> login(@Field("login") String loginName,
                               @Field("password") String loginPassword);


//    @GET("session/csrf.json")
//    void getToken(@Query("_") long timeStamp, Callback<Token> callback);

    @GET("session/csrf.json")
    Observable<Response<Token>> getToken(@Query("_") long timeStamp);

}
