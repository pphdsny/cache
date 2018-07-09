package com.pphdsny.lib.cache.util;

import android.content.Context;
import android.text.TextUtils;

import com.pphdsny.lib.cache.protocal.ECache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 */
public class CacheUtil {

    /**
     * 获取内存缓存key
     *
     * @param params
     * @return
     */
    public static String getMemoryCacheKey(Map params) {
        if (params == null) {
            return "";
        }
        if (params.containsKey(ECache.MEMORY_KEY)) {
            return params.get(ECache.MEMORY_KEY).toString();
        }
        if (params.containsKey(ECache.CACHE_KEY)) {
            return params.get(ECache.CACHE_KEY).toString();
        }
        return "";
    }

    /**
     * 获取本地存储Key
     *
     * @param params
     * @return
     */
    public static String getLocalCacheKey(Map params) {
        if (params == null) {
            return "";
        }
        if (params.containsKey(ECache.LOCAL_KEY)) {
            return params.get(ECache.LOCAL_KEY).toString();
        }
        if (params.containsKey(ECache.CACHE_KEY)) {
            return params.get(ECache.CACHE_KEY).toString();
        }
        return "";
    }

    /**
     * 获取本地资源存储Key
     *
     * @param params
     * @return
     */
    public static String getAssetCacheKey(Map params) {
        if (params == null) {
            return "";
        }
        if (params.containsKey(ECache.ASSET_KEY)) {
            return params.get(ECache.ASSET_KEY).toString();
        }
        if (params.containsKey(ECache.CACHE_KEY)) {
            return params.get(ECache.CACHE_KEY).toString();
        }
        return "";
    }

    /**
     * 获取缓存cacheContext
     *
     * @param params
     * @return
     */
    public static Context getCacheContext(Map params) {
        if (params == null) {
            return null;
        }
        Object cacheContext = params.get(ECache.CONTEXT_KEY);
        if (cacheContext == null || !(cacheContext instanceof Context)) {
            return null;
        }
        Context context = (Context) cacheContext;
        return context;
    }

    /**
     * 错误返回
     *
     * @param errorMsg
     * @return
     */
    public static Observable error(String errorMsg) {
        return Observable.error(new Throwable(errorMsg));
    }

    /**
     * 读取asset中的文本数据流
     *
     * @param context
     * @param assetFileName
     * @return
     */
    public static String readAssetString(Context context, String assetFileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = context.getAssets().open(assetFileName);
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");

            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder("");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 更新内存缓存
     *
     * @param key
     * @param value
     */
    public static void updateMemoryCache(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            GlobalMemoryCache.getInstance().put(key, value);
        }
    }

    /**
     * 更新本地缓存
     *
     * @param cacheContext
     * @param key
     * @param value
     */
    public static void updateLocalCache(Context cacheContext, String key, String value) {
        if (!TextUtils.isEmpty(key) && cacheContext != null) {
            CacheSPUtil.put(cacheContext, key, value);
        }
    }

}
