package com.togo.home.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Concrete implementation to load packages from the data sources into a cache.
 * <p/>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database
 * is not the latest.
 */

public class PagesRepository implements PagesDataSource {

    @Nullable
    private static PagesRepository INSTANCE = null;

    @NonNull
    private final PagesDataSource packagesRemoteDataSource;

    @NonNull
    private final PagesDataSource packagesLocalDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation
    private PagesRepository(@NonNull PagesDataSource packagesRemoteDataSource,
                            @NonNull PagesDataSource packagesLocalDataSource) {
        this.packagesRemoteDataSource = packagesRemoteDataSource;
        this.packagesLocalDataSource = packagesLocalDataSource;
    }

    // The access for other classes.
    public static PagesRepository getInstance(@NonNull PagesDataSource packagesRemoteDataSource,
                                              @NonNull PagesDataSource packagesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PagesRepository(packagesRemoteDataSource, packagesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * It is designed to gotten the packages from both database
     * and network. Which are faster then return them.
     * But in our app, we need not to update the data by accessing the network
     * when user enter the app every time because we run a service in backyard.
     * So we just return the data from database.
     * But in future, it may change.
     * @return Packages from {@link .local.PackagesLocalDataSource}.
     */
    @Override
    public Observable<List<PatientFirstPageModel>> getFirstPageModels() {
        if (mCacheIsDirty) {
            return packagesRemoteDataSource.getFirstPageModels().flatMap(new Function<List<PatientFirstPageModel>, ObservableSource<List<PatientFirstPageModel>>>() {
                @Override
                public ObservableSource<List<PatientFirstPageModel>> apply(List<PatientFirstPageModel> packages) throws Exception {
                    return Observable
                            .fromIterable(packages)
                            .doOnNext(new Consumer<PatientFirstPageModel>() {
                                @Override
                                public void accept(PatientFirstPageModel aPackage) throws Exception {
                                    packagesLocalDataSource.saveFirstPageModel(aPackage);
                                }
                            })
                            .toList()
                            .toObservable()
                            .doOnTerminate(new Action() {
                                @Override
                                public void run() throws Exception {
                                    mCacheIsDirty = false;
                                }
                            });
                }
            });
        } else {
            return packagesLocalDataSource.getFirstPageModels();
        }
    }

    @Override
    public void refreshFirstPageModels() {
        mCacheIsDirty = true;
    }

    @Override
    public void saveFirstPageModel(PatientFirstPageModel pageModel) {
        packagesRemoteDataSource.saveFirstPageModel(pageModel);
        packagesLocalDataSource.saveFirstPageModel(pageModel);
    }
}