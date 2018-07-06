package com.pp.lib.cache.protocal;

import java.util.Map;

/**
 * Created by wangpeng on 2018/6/29.
 * 定义cache请求需要参数
 */
public interface ICacheParams<T> {

    Map getParams();

    Class<T> getDataClass();
}
