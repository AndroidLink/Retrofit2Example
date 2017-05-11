package com.togo.home.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.togo.home.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangfeng on 17-3-30.
 */
public class ServiceGenerator
{
    private TogoService apiService;

    public ServiceGenerator()
    {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
            .create();

        Retrofit.Builder retrofitBuilder
                = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        OkHttpClient defaultOkHttpClient
                = new OkHttpClient.Builder()
//                .cache(getCache())
                .build();

        OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();

        // todo: wrap all common param within a interceptor rather than spreading
        // all over every api methods.
//        if(networkInterceptor != null){
//            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
//        }
        okHttpClientBuilder.addNetworkInterceptor(new AuthorizedNetworkInterceptor());

        OkHttpClient modifiedOkHttpClient = okHttpClientBuilder
                .addInterceptor(getHttpLoggingInterceptor())
                .build();

        retrofitBuilder.client(modifiedOkHttpClient);
        retrofitBuilder.baseUrl(TogoService.BASE_URL);

        Retrofit retrofit = retrofitBuilder.build();
        apiService = retrofit.create(TogoService.class);
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    public TogoService getServiceInstance()
    {
        return apiService;
    }
}
