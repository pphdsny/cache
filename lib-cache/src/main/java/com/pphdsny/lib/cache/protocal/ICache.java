package com.pphdsny.lib.cache.protocal;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * 全局cache的协议
 */
public interface ICache<T> {

    Observable<T> getData(ICacheParams<T> cacheParams);

    void saveData(ICacheParams<T> cacheParams,T t);
}
