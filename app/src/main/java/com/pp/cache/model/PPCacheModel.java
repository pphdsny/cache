package com.pp.cache.model;

/**
 * Created by wangpeng on 2018/7/6.
 * 注意此model应该被混淆，否则gson解析会失败
 */
public class PPCacheModel {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PPCacheModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
