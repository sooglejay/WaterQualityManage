package com.gaoxian.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jiangwei on 15/9/25.
 */
public class PreferenceUtil {
    private static final String PREFERENCE_NAME = "gaoxian_";

    public static void save(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static void save(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static void save(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static void save(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }


    public static void save(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }


    public static int load(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);

    }

    public static String load(Context context, String key, String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);

    }

    public static boolean load(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(key, defaultValue);

    }

    public static float load(Context context, String key, float defaultValue) {
        return getSharedPreferences(context).getFloat(key, defaultValue);

    }

    public static long load(Context context, String key, long defaultValue) {
        return getSharedPreferences(context).getLong(key, defaultValue);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        return editor;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (context != null) {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return null;
    }
    public static void clearAll(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }
}
