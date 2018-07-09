package com.pp.cache.impl;

import com.pp.cache.model.PPCacheModel;
import com.pphdsny.lib.cache.impl.NetCache;
import com.pphdsny.lib.cache.protocal.ICacheParams;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/6.
 */
public class PPNetCache extends NetCache<PPCacheModel> {

    @Override
    protected Observable<PPCacheModel> getDataImpl(ICacheParams<PPCacheModel> cacheParams) {
        //模拟了网络请求，实际情况可根据自己使用的网络库配合使用
        //如果用的是Retrofit+RxJava，那可以无缝对接
        PPCacheModel ppCacheModel = new PPCacheModel();
        ppCacheModel.setId(1);
        ppCacheModel.setName("网络模拟数据");
        return Observable.just(ppCacheModel);
    }

    @Override
    protected void saveDataIml(ICacheParams<PPCacheModel> cacheParams, PPCacheModel ppCacheModel) {

    }
}
