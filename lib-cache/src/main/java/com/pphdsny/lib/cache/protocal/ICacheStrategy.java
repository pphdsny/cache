package com.pphdsny.lib.cache.protocal;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/9.
 * 缓存策略
 */
public interface ICacheStrategy<T> {

    Observable<T> getDataStrategy(ICacheParams<T> cacheParams);

    void saveDataStrategy(ICacheParams<T> cacheParams,T t);

}
