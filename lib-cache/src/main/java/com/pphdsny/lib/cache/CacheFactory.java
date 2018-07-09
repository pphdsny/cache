package com.pphdsny.lib.cache;

import com.pphdsny.lib.cache.impl.DefaultCacheStrategy;
import com.pphdsny.lib.cache.protocal.ICache;
import com.pphdsny.lib.cache.protocal.ICacheParams;
import com.pphdsny.lib.cache.protocal.ICacheStrategy;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * Cache工厂类，获取Cache数据
 * Cache的执行顺序可以自由控制
 */
public class CacheFactory<T> {

    private ICacheStrategy<T> cacheStrategy;

    public CacheFactory(ICache<T>... cacheArr) {
        this.cacheStrategy = new DefaultCacheStrategy<>(cacheArr);
    }

    public CacheFactory(ICacheStrategy<T> cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public Observable<T> getData(ICacheParams<T> cacheParams) {
        return cacheStrategy.getDataStrategy(cacheParams);
    }

    public void saveData(ICacheParams<T> cacheParams, T t) {
        cacheStrategy.saveDataStrategy(cacheParams, t);
    }
}
