package com.mmga.cocode.data;

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


    public static <T> T createCocodeService(final Class<T> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(CocodeApi.SERVICE_BASE_URL)
                .build();

        return retrofit.create(serviceClass);
    }

}
