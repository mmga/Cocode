package com.mmga.cocode.data.data;

import com.mmga.cocode.data.data.model.CocodeData;
import com.mmga.cocode.data.data.provider.Token;

import retrofit.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface CocodeApi {

    String SERVICE_BASE_URL = "http://cocode.cc/";


    @GET("{tab}.json")
    Observable<Response<CocodeData>> getLatestData(@Header("Cookie") String cookie,
                                                   @Path("tab") String tab,
                                                   @Query("page") int page);

    @FormUrlEncoded
    @POST("session")
    Observable<Response<AuthState>> login(@Header("Cookie") String cookie,
                                          @Header("X-CSRF-Token") String token,
                                          @Header("X-Requested-With") String requestFormat,
                                          @Field("login") String loginName,
                                          @Field("password") String loginPassword);


    @GET("session/csrf.json")
    Observable<Response<Token>> getToken(@Query("_") long timeStamp);

}
