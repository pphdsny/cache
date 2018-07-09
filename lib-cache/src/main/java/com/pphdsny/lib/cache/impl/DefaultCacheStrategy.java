package com.pphdsny.lib.cache.impl;

import com.pphdsny.lib.cache.protocal.ICache;
import com.pphdsny.lib.cache.protocal.ICacheParams;
import com.pphdsny.lib.cache.protocal.ICacheStrategy;
import com.pphdsny.lib.cache.util.CacheUtil;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/9.
 */
public class DefaultCacheStrategy<T> implements ICacheStrategy<T> {

    protected ICache<T>[] cacheArr;

    public DefaultCacheStrategy(ICache<T>... cacheArr) {
        this.cacheArr = cacheArr;
    }

    @Override
    public Observable<T> getDataStrategy(ICacheParams<T> cacheParams) {
        ICache[] sortCacheList = getSortCacheList();
        if (sortCacheList == null || sortCacheList.length == 0) {
            return CacheUtil.error("cacheArr is empty!");
        }

        //默认按顺序读取
        Observable<T> retObservable;
        retObservable = sortCacheList[0].getData(cacheParams);
        for (int i = 1; i < sortCacheList.length; i++) {
            retObservable = retObservable.onErrorResumeNext(sortCacheList[i].getData(cacheParams));
        }
        return retObservable;
    }

    /**
     * 获取排序执行的顺序
     *
     * @return
     */
    protected ICache[] getSortCacheList() {
        return cacheArr;
    }

    @Override
    public void saveDataStrategy(ICacheParams<T> cacheParams, T t) {
        ICache[] sortCacheList = getSortCacheList();
        if (sortCacheList == null || sortCacheList.length == 0) {
            return;
        }
        //默认全量更新
        for (int i = 0; i < sortCacheList.length; i++) {
            sortCacheList[i].saveData(cacheParams, t);
        }
    }
}
