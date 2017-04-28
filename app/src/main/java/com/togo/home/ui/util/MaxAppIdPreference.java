package com.togo.home.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangfeng on 17-4-28.
 */

public class MaxAppIdPreference {
    private static final String PREF_NAME = "MaxAppIdPreference";
    private static final String KEY_LAST_APP = "KEY_LAST_APP";
    private static final int VALUE_DEFAULT_LAST_APP = 2;

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getSharedPreferenceEditor(Context context) {
        return getSharedPreference(context).edit();
    }

    public static void saveLastIndex(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreferenceEditor(context);
        editor.putInt(KEY_LAST_APP, id);
        editor.commit();
    }

    public static int getLastIndex(Context context) {
        return getSharedPreference(context).getInt(KEY_LAST_APP, VALUE_DEFAULT_LAST_APP);
    }
}
