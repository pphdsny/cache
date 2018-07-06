package com.pp.cache.impl;

import com.pp.lib.cache.impl.MemoryCache;
import com.pp.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/6.
 */
public class PPMemoryInvalidCache extends MemoryCache {

    @Override
    protected Observable getDataImpl(Map params, Class dataClass) {
        return CacheUtil.error("直接报错，无效内存缓存");
    }
}
