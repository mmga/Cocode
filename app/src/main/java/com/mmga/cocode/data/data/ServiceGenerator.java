package com.mmga.cocode.data.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static <T> T createCocodeService(final Class<T> serviceClass) {
        Retrofit retrofit = builder
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://cocode.cc/")
                .build();

        return retrofit.create(serviceClass);
    }

}
