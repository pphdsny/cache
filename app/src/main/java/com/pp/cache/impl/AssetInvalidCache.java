package com.pp.cache.impl;

import com.pp.lib.cache.impl.MemoryCache;
import com.pphdsny.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/7/6.
 */
public class AssetInvalidCache extends MemoryCache {

    @Override
    protected Observable getDataImpl(Map params, Class dataClass) {
        return CacheUtil.error("直接报错，无效Asset缓存");
    }
}
