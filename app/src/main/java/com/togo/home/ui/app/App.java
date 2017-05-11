package com.togo.home.ui.app;

import android.app.Application;

import com.togo.home.data.retrofit.ServiceGenerator;

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
