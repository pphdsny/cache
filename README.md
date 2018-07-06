# 构建灵活的缓存机制

## 前言

某天，做搜索的时候，期望筛选条件的配置文件能缓存起来，不要每次都从网络获取，减少异常的发生。于是乎刷刷的开始写大量的判断逻辑if/else，做了个三级缓存，从内存、网络、本地读取数据，写完了测试也通过了，非常完美！！！

突然说要加个本地资源（asset）缓存，没问题，又加了段if/else...

哪天缓存的顺序又期望改成"网络->内存->本地->资源文件"，好...改...

后来某个需求也想用缓存，但没有本地和资源文件缓存，难道又得重新来一遍，瞬间GG...

我们是不是该理一理，顺一顺，构建个灵活的缓存机制！

## 理一理需求

-   需要缓存
-   需要多级缓存
-   需要缓存策略可变
-   需要可以自定义缓存
-   需要使用时候简单便捷

## 顺一顺实现

![Cahce](https://upload-images.jianshu.io/upload_images/2014593-be408e81987c0db1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

##简要说明

-   设计思路是工厂模式和责任链模式的结合
-   基于RxJava的数据流
-   ICahce定义了一个getData请求方法，自定义缓存策略可以实现
-   请求参数有ICacheParams（是否开启各级别缓存、业务参数等）
-   默认实现了内存、网络、本地、资源Asset的缓存
    -   内存缓存利用LruCache
    -   网络缓存利用eTag
    -   本地缓存利用SP存储
    -   资源Asset缓存利用本地文件
-   通过CacheFactory进行缓存策略组装，基于RxJava的error捕获，将处理转到一下个cache中进行处理

## 核心代码

### ICache

```java
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
```

### ICacheParams

```java
/**
 * Created by wangpeng on 2018/6/29.
 * 定义cache请求需要参数
 */
public interface ICacheParams<T> {

    Map getParams();

    Class<T> getDataClass();
}
```

### CacheFactory

```java
/**
 * Created by wangpeng on 2018/6/28.
 * Cache工厂类，获取Cache数据
 * Cache的执行顺序可以自由控制
 */
public class CacheFactory<T> {

    private ICache<T>[] cacheArr;

    public CacheFactory(@NonNull ICache<T>... cacheArr) {
      //缓存的执行顺序和数组一直
        this.cacheArr = cacheArr;
    }

    public Observable<T> requestCache(ICacheParams<T> cacheParams) {
        if (cacheArr == null || cacheArr.length == 0) {
            return Observable.empty();
        }
        Observable<T> retObservable;
        retObservable = cacheArr[0].getData(cacheParams);
        for (int i = 1; i < cacheArr.length; i++) {
            retObservable = retObservable.onErrorResumeNext(cacheArr[i].getData(cacheParams));
        }
        return retObservable;
    }
}
```

## 应用场景

-   一些配置文件的缓存
-   可以结合网络请求，一键缓存业务数据

## 如何用

### Gradle依赖

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.pphdsny:cache:1.0.1'
    implementation 'io.reactivex:rxjava:1.2.2'
    implementation 'com.google.code.gson:gson:2.6.2'
}
```

### 使用

```
CacheFactory cacheFactory = new CacheFactory(
                new MemoryCache(),
                new NetCache() {
                    @Override
                    protected Observable getDataImpl(Map params, Class dataClass) {
                        return null;
                    }
                },
                new LocalCache(),
                new AssetCache()
        );
        cacheFactory.requestCache(cacheParams)
                .subscribe(new Action1<PPCacheModel>() {
                    @Override
                    public void call(PPCacheModel ppCacheModel) {
                    //执行正确
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    //执行出错
                    }
                });
```

更多用法详见[demo](https://github.com/pphdsny/cache/tree/master/app)

### 其他

如果你正好要做缓存，也是使用RxJava，这个方案可供参考。欢迎拍砖。

### Blog地址

https://www.jianshu.com/p/a9ae44869294