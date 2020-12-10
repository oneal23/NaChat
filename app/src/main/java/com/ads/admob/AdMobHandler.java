package com.ads.admob;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ads.IAdsHandler;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AdMobHandler implements IAdsHandler {

    private static final String TAG = "AdMobHandler";
    private boolean isInit = false;
    private ArrayList<AdMobItem> adMobItems;
    private Object lastObject;

    @Override
    public void init(Context context) {
        MobileAds.initialize(context.getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e(TAG, "onInitializationComplete");
                isInit = true;
                onLoad();
            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("F0B55ADC7652AF25A0425745B5C1F6B3")).build());
    }

    @Override
    public void addBannerAd(ViewGroup layout, String adUnitId) {
        if(layout != null && !TextUtils.isEmpty(adUnitId)) {
            lastObject = layout;
            AdMobBannerItem item = new AdMobBannerItem();
            item.setAdUnitId(adUnitId);
            item.setLayout(layout);
            addItem(item);
            if(isInit) {
                loadItem(item);
            }
        }
    }

    @Override
    public void addInterstitialAd(Context context, String adUnitId) {
        if(context != null && !TextUtils.isEmpty(adUnitId)) {
            this.lastObject = context;
            AdMobInterstitialItem item = getInterstitialAd(context, adUnitId);
            if (item == null) {
                item = new AdMobInterstitialItem();
                item.setAdUnitId(adUnitId);
                item.setContext(context);
                addItem(item);
            }
            if (isInit) {
                loadItem(item);
            }
        }
    }

    @Override
    public void showIntersitialAd(Context context, String adUnitId) {
        AdMobInterstitialItem item = getInterstitialAd(context, adUnitId);
        if (item != null) {
            showItem(item);
        }
    }

    @Override
    public void addRewardAd(Context context, String adUnitId) {
        if(context != null && !TextUtils.isEmpty(adUnitId)) {
            this.lastObject = context;
            AdMobRewardItem item = getAdMobRewardAd(context, adUnitId);
            if (item == null) {
                item = new AdMobRewardItem();
                item.setAdUnitId(adUnitId);
                item.setContext(context);
                addItem(item);
            }
            if (isInit) {
                loadItem(item);
            }
        }
    }

    @Override
    public void showRewardAd(Context context, String adUnitId) {
        AdMobRewardItem item = getAdMobRewardAd(context, adUnitId);
        if (item != null) {
            showItem(item);
        }
    }

    @Override
    public void addNativeAd(ViewGroup layout, String adUnitId) {
        if(layout != null && !TextUtils.isEmpty(adUnitId)) {
            lastObject = layout;
            AdMobNativeItem item = new AdMobNativeItem();
            item.setAdUnitId(adUnitId);
            item.setLayout(layout);
            addItem(item);
            if(isInit) {
                loadItem(item);
            }
        }
    }

    @Override
    public void addAppOpenAd(Application application, String adUnitId) {
        if(application != null && !TextUtils.isEmpty(adUnitId)) {
            lastObject = application;
            AdMobAppOpenItem item = new AdMobAppOpenItem();
            item.init(application, adUnitId);
            addItem(item);
            if (isInit) {
                item.load();
            }
        }
    }

    private AdMobInterstitialItem getInterstitialAd(Context context, String adUnitId) {
        if(adMobItems != null) {
            for (int i = 0; i < adMobItems.size(); ++i) {
                AdMobItem item = adMobItems.get(i);
                if (item instanceof AdMobInterstitialItem) {
                    AdMobInterstitialItem interstitialItem = (AdMobInterstitialItem) item;
                    if (interstitialItem.getContext() == context && adUnitId.equals(interstitialItem.getAdUnitId())) {
                        return interstitialItem;
                    }
                }
            }
        }
        return null;
    }

    private AdMobRewardItem getAdMobRewardAd(Context context, String adUnitId) {
        if(adMobItems != null) {
            for (int i = 0; i < adMobItems.size(); ++i) {
                AdMobItem item = adMobItems.get(i);
                if (item instanceof AdMobRewardItem) {
                    AdMobRewardItem rewardItem = (AdMobRewardItem) item;
                    if (rewardItem.getContext() == context && adUnitId.equals(rewardItem.getAdUnitId())) {
                        return rewardItem;
                    }
                }
            }
        }
        return null;
    }

    private void addItem(AdMobItem item) {
        if (adMobItems == null) {
            adMobItems = new ArrayList<>();
        }
        adMobItems.add(item);
    }

    private void onLoad() {
        if (adMobItems != null && lastObject != null) {
            for (AdMobItem item : adMobItems) {
                if ((item.getObject() != null && item.getObject() == lastObject) || (item instanceof AdMobAppOpenItem)) {
                    loadItem(item);
                }
            }
            lastObject = null;
        }
    }

    private void loadItem(AdMobItem item) {
        if (item != null) {
            item.load();
        }
    }

    private void showItem(AdMobItem item) {
        if (item != null) {
            item.show();
        }
    }

    @Override
    public void onResume(Context context) {
        if (adMobItems != null) {
            for (AdMobItem item: adMobItems) {
                item.resume(context);
            }
        }
    }

    @Override
    public void onPause(Context context) {
        if (adMobItems != null) {
            for (AdMobItem item: adMobItems) {
                item.pause(context);
            }
        }
    }

    @Override
    public void onDestroy(Context context) {
        if (adMobItems != null) {
            for (int i = 0; i < adMobItems.size(); ++i) {
                AdMobItem item = adMobItems.get(i);
                item.destroy(context);
                adMobItems.remove(i);
            }
        }
    }
}
