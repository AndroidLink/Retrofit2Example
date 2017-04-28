package com.togo.home.ui.activity;

import android.content.Context;

import com.togo.home.ui.util.AppFinder;
import com.togo.home.ui.util.MaxAppIdPreference;

/**
 * Created by yangfeng on 17-4-28.
 */

public class ScopeHelperImpl implements AppFinder.ScopeHelper {
    private final Context context;
    public ScopeHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public int startIndex() {
        return MaxAppIdPreference.getLastIndex(context);
    }

    @Override
    public void saveLastIndex(int id) {
        MaxAppIdPreference.saveLastIndex(context, id);
    }
}
