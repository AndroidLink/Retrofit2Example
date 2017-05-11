package com.togo.home.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;
import com.togo.home.data.source.PagesDataSource;
import com.togo.home.data.source.local.PagesPersistenceContract.PageEntry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

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

    @NonNull
    private PatientFirstPageModel getFirstPageModel(@NonNull Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(PagesPersistenceContract.PageEntry.COLUMN_NAME_ENTRY_ID));
        String title = c.getString(c.getColumnIndexOrThrow(PagesPersistenceContract.PageEntry.COLUMN_NAME_TITLE));
        String cover = c.getString(c.getColumnIndexOrThrow(PageEntry.COLUMN_NAME_COVER));
        String description =
                c.getString(c.getColumnIndexOrThrow(PagesPersistenceContract.PageEntry.COLUMN_NAME_DESCRIPTION));
        String completed =
                c.getString(c.getColumnIndexOrThrow(PagesPersistenceContract.PageEntry.COLUMN_NAME_COMPLETED));
        PatientFirstPageModel pageModel = new PatientFirstPageModel();
        pageModel.setHospital_guid(itemId);
        pageModel.setDisplayName(title);
        pageModel.setCoverPhoto(cover);
        pageModel.setServiceLine(description);
        pageModel.setEmergentLine(completed);
        return pageModel;
    }

    /**
     * Get the packages in database and sort them in timestamp descending.
     * @return The observable packages from database.
     */
    @Override
    public Observable<List<PatientFirstPageModel>> getFirstPageModels() {
        // todo: add code like this
        String[] projection = {
                PageEntry.COLUMN_NAME_ENTRY_ID,
                PagesPersistenceContract.PageEntry.COLUMN_NAME_TITLE,
                PagesPersistenceContract.PageEntry.COLUMN_NAME_COVER,
                PageEntry.COLUMN_NAME_DESCRIPTION,
                PagesPersistenceContract.PageEntry.COLUMN_NAME_COMPLETED
        };
//        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), PageEntry.TABLE_NAME);
//        return mDatabaseHelper.createQuery(PageEntry.TABLE_NAME, sql)
//                .mapToList(mTaskMapperFunction);

        List<PatientFirstPageModel> tasks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(
                PagesPersistenceContract.PageEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                PatientFirstPageModel task = getFirstPageModel(c);
                tasks.add(task);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();
        return Observable.just(tasks);
    }

    @Override
    public void refreshFirstPageModels() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void saveFirstPageModel(PatientFirstPageModel pageModel) {
        // todo: save like this
        checkNotNull(pageModel);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_ENTRY_ID, pageModel.getHospital_guid());
        values.put(PageEntry.COLUMN_NAME_TITLE, pageModel.getDisplayName());
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_COVER, pageModel.getCoverPhoto());
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_DESCRIPTION, pageModel.getServiceLine());
        values.put(PageEntry.COLUMN_NAME_COMPLETED, pageModel.getEmergentLine());

        db.insert(PagesPersistenceContract.PageEntry.TABLE_NAME, null, values);

        db.close();
    }

}