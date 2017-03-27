package com.andexert.retrofitexample.rest.service;

import com.andexert.retrofitexample.rest.model.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Author :    Chutaux Robin
 * Date :      10/2/2014
 *
 * Modifier:
 */
public interface WeatherService {
    @GET("/data/2.5/weather")
    Observable<ApiResponse> getWeather(@Query("q") String strCity);
}
