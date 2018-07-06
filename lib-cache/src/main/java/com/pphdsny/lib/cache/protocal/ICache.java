package com.pphdsny.lib.cache.protocal;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * 全局cache的协议
 */
public interface ICache<T> {

    //map中cache的key
    String CACHE_KEY = "cache_key";
    //map中内存cache的key，如没有设置会以CACHE_KEY作为key
    String MEMORY_KEY = "memory_key";
    //map中本地cache的key，如没有设置会以CACHE_KEY作为key
    String LOCAL_KEY = "local_key";
    //map中资源cache的key，如没有设置会以CACHE_KEY作为key
    String ASSET_KEY = "asset_key";
    //Context对象
    String CONTEXT_KEY = "context_key";

    Observable<T> getData(ICacheParams<T> cacheParams);
}
