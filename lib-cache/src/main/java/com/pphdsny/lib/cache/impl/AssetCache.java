package com.pphdsny.lib.cache.impl;

import android.content.Context;
import android.text.TextUtils;

import com.pphdsny.lib.cache.protocal.ICacheParams;
import com.pphdsny.lib.cache.util.CacheUtil;

import java.util.Map;

import rx.Observable;

/**
 * Created by wangpeng on 2018/6/28.
 * 本地默认资源文件缓存（asset下面存放的）
 */
public class AssetCache<T> extends AbstractCache<T> {

    @Override
    protected Observable<T> getDataImpl(ICacheParams<T> cacheParams) {
        Map<String, Object> params = cacheParams.getParams();
        String assetCacheKey = CacheUtil.getAssetCacheKey(params);
        if (params == null || TextUtils.isEmpty(assetCacheKey)) {
            return CacheUtil.error("未开启资源缓存");
        }
        Context context = CacheUtil.getCacheContext(params);
        if (context == null) {
            return CacheUtil.error("使用资源缓存，必须配置Context，请在params中设置ICache.CONTEXT_KEY");
        }
        String cacheValue = CacheUtil.readAssetString(context, assetCacheKey);
        if (TextUtils.isEmpty(cacheValue)) {
            return CacheUtil.error("未获取到有效资源缓存");
        }
        T t = fromJson(params, cacheValue, cacheParams.getDataClass());
        if (t == null) {
            return CacheUtil.error("资源缓存数据解析出错");
        }
        return Observable.just(t);
    }

    @Override
    protected void saveDataIml(ICacheParams<T> cacheParams, T t) {
        //默认不更新本地缓存
    }
}
