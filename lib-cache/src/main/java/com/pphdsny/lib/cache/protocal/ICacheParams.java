package com.pphdsny.lib.cache.protocal;

import java.util.Map;

/**
 * Created by wangpeng on 2018/6/29.
 * 定义cache请求需要参数
 */
public interface ICacheParams<T> {

    /**
     * 缓存带参
     *
     * @return
     */
    Map<String, Object> getParams();

    /**
     * 获取缓存需实例化的类
     *
     * @return
     */
    Class<T> getDataClass();

    /**
     * 设置缓存时间，单位是ms
     *
     * @return
     */
    long getExpityTime();
}
