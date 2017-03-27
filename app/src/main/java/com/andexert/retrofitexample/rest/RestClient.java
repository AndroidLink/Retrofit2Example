package com.andexert.retrofitexample.rest;

import com.andexert.retrofitexample.BuildConfig;
import com.andexert.retrofitexample.rest.service.AuthorizedNetworkInterceptor;
import com.andexert.retrofitexample.rest.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author :    Chutaux Robin
 * Date :      10/2/2014
 */
public class RestClient
{
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private WeatherService apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
            .create();

        Retrofit.Builder retrofitBuilder
                = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
        retrofitBuilder.baseUrl(BASE_URL);

        Retrofit retrofit = retrofitBuilder.build();
        apiService = retrofit.create(WeatherService.class);
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

    public WeatherService getWeatherService()
    {
        return apiService;
    }
}
