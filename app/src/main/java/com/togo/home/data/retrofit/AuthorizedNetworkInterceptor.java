package com.togo.home.data.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangfeng on 17-3-27.
 */

public class AuthorizedNetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null) {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl.Builder httpUrlBuilder = originalHttpUrl.newBuilder();

            httpUrlBuilder.addQueryParameter(ApiConstant.APP_KEY, ApiConstant.APP_ID);

            httpUrlBuilder.addQueryParameter("app_key", "taogu");
            httpUrlBuilder.addQueryParameter("osversion", "21");
            httpUrlBuilder.addQueryParameter("token", "");
            httpUrlBuilder.addQueryParameter("uuid", "");
            httpUrlBuilder.addQueryParameter("version", "170");
            httpUrlBuilder.addQueryParameter("resolution", "1080x1920");
            httpUrlBuilder.addQueryParameter("net", "TYPE_WIFI");
//            httpUrlBuilder.addQueryParameter("app", "4");
            httpUrlBuilder.addQueryParameter("os", "ANDROID");
            httpUrlBuilder.addQueryParameter("model", "Redmi Note 3");

            HttpUrl httpUrl = httpUrlBuilder.build();
            Request.Builder requestBuilder = originalRequest.newBuilder().url(httpUrl);
            Request modifiedRequest = requestBuilder.build();


            return chain.proceed(modifiedRequest);
        }

        return null;
    }
}
