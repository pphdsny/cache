package com.pphdsny.lib.cache;

import com.pp.lib.cache.protocal.ICache;
import com.pp.lib.cache.protocal.ICacheParams;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * Cache工厂类，获取Cache数据
 * Cache的执行顺序可以自由控制
 */
public class CacheFactory<T> {

    private ICache<T>[] cacheArr;

    public CacheFactory(ICache<T>... cacheArr) {
        this.cacheArr = cacheArr;
    }

    public Observable<T> requestCache(ICacheParams<T> cacheParams) {
        if (cacheArr == null || cacheArr.length == 0) {
            return Observable.empty();
        }
        Observable<T> retObservable;
        retObservable = cacheArr[0].getData(cacheParams);
        for (int i = 1; i < cacheArr.length; i++) {
            retObservable = retObservable.onErrorResumeNext(cacheArr[i].getData(cacheParams));
        }
        return retObservable;
    }
}
