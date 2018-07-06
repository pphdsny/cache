package com.pphdsny.lib.cache.impl;

import android.content.Context;
import android.text.TextUtils;

import com.pphdsny.lib.cache.protocal.ICache;
import com.pphdsny.lib.cache.util.CacheSPUtil;
import com.pphdsny.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * 本地缓存,默认存SP
 * 设置了缓存key{@link ICache#LOCAL_KEY}则默认开启本地缓存，不同业务逻辑子类可复写
 */
public class LocalCache<T> extends AbstractCache<T> {
    @Override
    protected Observable<T> getDataImpl(Map params, Class<T> dataClass) {
        String localCacheKey = CacheUtil.getLocalCacheKey(params);
        if (params == null || TextUtils.isEmpty(localCacheKey)) {
            return CacheUtil.error("未开启本地缓存");
        }
        Object cacheContext = params.get(ICache.CONTEXT_KEY);
        if (cacheContext == null || !(cacheContext instanceof Context)) {
            return CacheUtil.error("使用本地缓存，必须配置Context，请在params中设置ICache.CONTEXT_KEY");
        }
        String cacheValue = CacheSPUtil.get((Context) cacheContext, localCacheKey);
        if (TextUtils.isEmpty(cacheValue)) {
            return CacheUtil.error("未获取到有效本地缓存");
        }
        T t = null;
        try {
            t = fromJson(params, cacheValue, dataClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (t == null) {
            return CacheUtil.error("本地缓存数据解析出错");
        }
        CacheUtil.updateMemoryCache(CacheUtil.getMemoryCacheKey(params), cacheValue);
        return Observable.just(t);
    }
}
