package com.pphdsny.lib.cache.impl;

import com.google.gson.Gson;
import com.pphdsny.lib.cache.protocal.ICache;
import com.pphdsny.lib.cache.protocal.ICacheParams;
import com.pphdsny.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/6.
 * 只是做了些数据异常处理
 */
public abstract class AbstractCache<T> implements ICache<T> {

    @Override
    public Observable<T> getData(ICacheParams<T> cacheParams) {
        if (cacheParams == null) {
            return CacheUtil.error("cacheParms object is null");
        }
        Map params = cacheParams.getParams();
        if (params == null) {
            return CacheUtil.error("cacheParms.getParams() return null");
        }
        Class<T> dataClass = cacheParams.getDataClass();
        if (dataClass == null) {
            return CacheUtil.error("cacheParms.getDataClass() return null");
        }
        return getDataImpl(params, cacheParams.getDataClass());
    }

    /**
     * 当保存的json和实例对象不一致时候，子类去复写
     *
     * @param params
     * @param json
     * @param tClass
     * @return
     */
    protected T fromJson(Map params, String json, Class<T> tClass) {
        T t = null;
        try {
            t = new Gson().fromJson(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    protected abstract Observable<T> getDataImpl(Map params, Class<T> dataClass);
}
