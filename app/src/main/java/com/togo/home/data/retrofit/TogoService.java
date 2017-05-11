package com.togo.home.data.retrofit;

import com.togo.home.data.model.SummaryWrapper;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by yangfeng on 17-3-30.
 */
public interface TogoService {
    String BASE_URL = "https://api.91taogu.com/";

    @FormUrlEncoded
    @POST("api?func=patientFirstPage")
    Observable<SummaryWrapper> fetchTogoHome(@Field("app") int app);
}
