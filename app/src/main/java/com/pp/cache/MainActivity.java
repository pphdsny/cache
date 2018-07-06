package com.pp.cache;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pp.cache.databinding.ActivityMainBinding;
import com.pp.cache.impl.AssetInvalidCache;
import com.pp.cache.model.PPCacheParams;
import com.pp.cache.impl.PPMemoryInvalidCache;
import com.pp.cache.impl.PPNetCache;
import com.pp.cache.impl.PPNetInvalidCache;
import com.pp.cache.model.PPCacheModel;
import com.pphdsny.lib.cache.CacheFactory;
import com.pphdsny.lib.cache.impl.AssetCache;
import com.pphdsny.lib.cache.impl.LocalCache;
import com.pphdsny.lib.cache.impl.MemoryCache;
import com.pphdsny.lib.cache.impl.NetCache;
import com.pphdsny.lib.cache.protocal.ICache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private List<ICache<PPCacheModel>> cacheList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        binding.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_memory:
                cacheList.add(new MemoryCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_memory_invalid:
                cacheList.add(new PPMemoryInvalidCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_net:
                cacheList.add(new PPNetCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_net_invalid:
                cacheList.add(new PPNetInvalidCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_local:
                cacheList.add(new LocalCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_local_invalid:
                cacheList.add(new PPNetInvalidCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_asset:
                cacheList.add(new AssetCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_asset_invalid:
                cacheList.add(new AssetInvalidCache());
                binding.tvContent.append(((Button) v).getText() + "\n");
                break;
            case R.id.btn_sure:
                clickSure();
                break;
            case R.id.btn_clear:
                cacheList.clear();
                binding.tvContent.setText("");
                break;
        }
    }

    private void clickSure() {
        PPCacheParams ppCacheParams = new PPCacheParams(this);
        ICache<PPCacheModel>[] cacheArr = new ICache[cacheList.size()];
        for (int i = 0; i < cacheList.size(); i++) {
            cacheArr[i] = cacheList.get(i);
        }

//        CacheFactory cacheFactory = new CacheFactory(cacheArr);
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
        cacheFactory.requestCache(ppCacheParams)
                .subscribe(new Action1<PPCacheModel>() {
                    @Override
                    public void call(PPCacheModel ppCacheModel) {
                        binding.tvContent.append("执行正确----" + ppCacheModel.toString() + "\n");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        binding.tvContent.append("执行出错----" + throwable.getMessage() + "\n");
                    }
                });
    }
}
