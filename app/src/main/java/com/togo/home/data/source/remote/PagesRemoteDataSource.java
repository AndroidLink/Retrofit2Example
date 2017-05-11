package com.togo.home.data.source.remote;

import android.support.annotation.Nullable;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;
import com.togo.home.data.source.PagesDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * Implementation of the data source that adds a latency simulating network.
 */

public class PagesRemoteDataSource implements PagesDataSource {

    @Nullable
    private static PagesRemoteDataSource INSTANCE;

    // Prevent direct instantiation
    private PagesRemoteDataSource() {

    }

    // Access this instance for outside classes.
    public static PagesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PagesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Destroy the instance.
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<PatientFirstPageModel>> getFirstPageModels() {
        // Not required because the {@link PagesRepository} handles the logic
        // of refreshing the packages from all available data source
        return Observable.just(Collections.<PatientFirstPageModel>emptyList());
    }

    @Override
    public void refreshFirstPageModels() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void saveFirstPageModel(PatientFirstPageModel pageModel) {
    }

}