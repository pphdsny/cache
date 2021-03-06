package com.pp.cache.impl;

import com.pp.cache.model.PPCacheModel;
import com.pphdsny.lib.cache.impl.NetCache;
import com.pphdsny.lib.cache.protocal.ICacheParams;
import com.pphdsny.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/6.
 */
public class PPNetInvalidCache extends NetCache<PPCacheModel> {

    @Override
    protected Observable<PPCacheModel> getDataImpl(ICacheParams<PPCacheModel> cacheParams) {
        return CacheUtil.error("直接报错，无效网络缓存");
    }

    @Override
    protected void saveDataIml(ICacheParams<PPCacheModel> cacheParams, PPCacheModel ppCacheModel) {

    }
}
