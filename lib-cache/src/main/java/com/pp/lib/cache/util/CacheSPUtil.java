package com.pp.lib.cache.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangpeng on 2018/6/28.
 */
public class CacheSPUtil {
    private static final String SP_NAME = "local_cache_storage";

    public static void put(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String get(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }
}
