package com.togo.home.data.remote;

import com.togo.home.data.model.ApiResponse;

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
