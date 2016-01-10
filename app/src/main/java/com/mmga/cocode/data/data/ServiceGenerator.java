package com.mmga.cocode.data.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class ServiceGenerator {

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();


    private static Retrofit.Builder builder = new Retrofit.Builder();

    public static <T> T createGetService(final Class<T> serviceClass) {
        return createGetService(serviceClass, "");
    }

    public static <T> T createGetService(final Class<T> serviceClass, final String cookie) {

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().clear();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Cookie", cookie);

                Request request = requestBuilder.build();
                return  chain.proceed(request);
            }
        });

        Retrofit retrofit = builder
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://cocode.cc/")
                .build();

        return retrofit.create(serviceClass);
    }



    public static <T> T createGetTokenService(final Class<T> serviceClass) {
        Retrofit retrofit = builder
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://cocode.cc/")
                .build();

        return retrofit.create(serviceClass);
    }



    public static <T> T createLoginService(Class<T> serviceClass, final String token, final String cookie) {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().clear();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Cookie", cookie)
//                        .header("Referer", "http://cocode.cc/")
//                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("X-CSRF-Token", token)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = builder.client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://cocode.cc/")
                .build();
        return retrofit.create(serviceClass);
    }


}
