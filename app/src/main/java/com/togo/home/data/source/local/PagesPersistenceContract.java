/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.togo.home.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class PagesPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private PagesPersistenceContract() {}

    public static List<PatientFirstPageModel> queryAll(SQLiteDatabase db) {
        String[] projection = PagesPersistenceContract.PROJECTION_FULL;

        List<PatientFirstPageModel> pages = new ArrayList<>();

        Cursor c = db.query(
                PagesPersistenceContract.PageEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                PatientFirstPageModel task = getFirstPageModel(c);
                pages.add(task);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();
        return pages;
    }

    public static void save(@NonNull SQLiteDatabase db, @NonNull PatientFirstPageModel pageModel) {
        checkNotNull(db);
        checkNotNull(pageModel);

        ContentValues values = new ContentValues();
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_ENTRY_ID, pageModel.getHospital_guid());
        values.put(PageEntry.COLUMN_NAME_TITLE, pageModel.getDisplayName());
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_COVER, pageModel.getCoverPhoto());
        values.put(PagesPersistenceContract.PageEntry.COLUMN_NAME_DESCRIPTION, pageModel.getServiceLine());
        values.put(PageEntry.COLUMN_NAME_COMPLETED, pageModel.getEmergentLine());

        db.insert(PagesPersistenceContract.PageEntry.TABLE_NAME, null, values);
        db.close();
    }

    /* Inner class that defines the table contents */
    public static abstract class PageEntry implements BaseColumns {
        public static final String TABLE_NAME = "firstpage";
        public static final String COLUMN_NAME_ENTRY_ID = "hospitalid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COVER = "cover";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETED = "completed";
    }

    public static final String[] PROJECTION_FULL = {
            PageEntry.COLUMN_NAME_ENTRY_ID,
            PagesPersistenceContract.PageEntry.COLUMN_NAME_TITLE,
            PagesPersistenceContract.PageEntry.COLUMN_NAME_COVER,
            PageEntry.COLUMN_NAME_DESCRIPTION,
            PagesPersistenceContract.PageEntry.COLUMN_NAME_COMPLETED
    };

    @NonNull
    private static PatientFirstPageModel getFirstPageModel(@NonNull Cursor c) {
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

}
