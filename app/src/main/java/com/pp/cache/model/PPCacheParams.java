package com.pp.cache.model;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.pphdsny.lib.cache.protocal.ECache;
import com.pphdsny.lib.cache.protocal.ICacheParams;

import java.util.Map;

/**
 * Created by wangpeng on 2018/7/6.
 * 在这里可以把我们需要的参数，和一些缓存配置都写在里面
 */
public class PPCacheParams implements ICacheParams<PPCacheModel> {

    private Context context;

    public PPCacheParams(Context context) {
        this.context = context;
    }

    @Override
    public Map getParams() {
        ArrayMap<Object, Object> map = new ArrayMap<>();
        //开启内存缓存，不写默认不存
        map.put(ECache.MEMORY_KEY, "pp_memory_key");
        //开启本地缓存，不写默认不存
        map.put(ECache.LOCAL_KEY, "pp_local_key");
        //开启Asset缓存，不写默认不存，注意key是Asset中的文件名称
        map.put(ECache.ASSET_KEY, "AssetCache.json");
        //注意，如果要使用本地缓存，必须要设置context
        map.put(ECache.CONTEXT_KEY, context);
        //也可以放一些自定义的业务参数
        map.put("id", 111);
        return map;
    }

    @Override
    public Class<PPCacheModel> getDataClass() {
        return PPCacheModel.class;
    }

    @Override
    public long getExpityTime() {
        return 0;
    }
}
