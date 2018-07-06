package com.pp.lib.cache.util;

import android.util.LruCache;

/**
 * Created by wangpeng on 2018/6/28.
 * 简单的全局缓存的cache对象，用于缓存一些数据对象
 * 复杂的缓存设计可以参考服务器用的redis或者其他
 */
public class GlobalMemoryCache extends LruCache<String, String> {

    //字节数
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private GlobalMemoryCache() {
        super(MAX_CACHE_SIZE);
    }

    private static class SingletonHolder {
        public static final GlobalMemoryCache sInstance = new GlobalMemoryCache();
    }

    public static GlobalMemoryCache getInstance() {
        return SingletonHolder.sInstance;
    }

    @Override
    protected int sizeOf(String key, String value) {
        int keySize = 0;
        if (key != null) {
            keySize = key.getBytes().length;
        }
        int valueSize = 0;
        if (value != null) {
            valueSize = value.getBytes().length;
        }
        return keySize + valueSize;
    }
}
