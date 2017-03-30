package com.togo.home.ui.app;

import android.app.Application;

import com.togo.home.data.remote.ServiceGenerator;

/**
 * Author :    Chutaux Robin
 * Date :      10/2/2014
 */
public class App extends Application
{
    private static ServiceGenerator restClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        restClient = new ServiceGenerator();
    }

    public static ServiceGenerator getRestClient()
    {
        return restClient;
    }
}
