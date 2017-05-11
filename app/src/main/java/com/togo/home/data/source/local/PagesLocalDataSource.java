package com.togo.home.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;
import com.togo.home.data.source.PagesDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Concrete implementation of a data source as a db.
 */

public class PagesLocalDataSource implements PagesDataSource {

    @Nullable
    private static PagesLocalDataSource INSTANCE;

    private PagesDbHelper mDbHelper;

    // Prevent direct instantiation
    private PagesLocalDataSource(@NonNull Context context) {
        mDbHelper = new PagesDbHelper(context);
    }

    // Access this instance for other classes.
    public static PagesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PagesLocalDataSource(context);
        }
        return INSTANCE;
    }

    // Destroy the instance.
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Get the packages in database and sort them in timestamp descending.
     * @return The observable packages from database.
     */
    @Override
    public Observable<List<PatientFirstPageModel>> getFirstPageModels() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return Observable.just(PagesPersistenceContract.queryAll(db));
    }

    @Override
    public void refreshFirstPageModels() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void saveFirstPageModel(@NonNull PatientFirstPageModel pageModel) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        PagesPersistenceContract.save(db, pageModel);
    }

}