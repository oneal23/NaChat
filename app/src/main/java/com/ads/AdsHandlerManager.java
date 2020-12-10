package com.ads;

import android.app.Application;
import android.content.Context;
import android.view.ViewGroup;

import com.ads.admob.AdMobHandler;

public class AdsHandlerManager implements IAdsHandler{

    private static volatile AdsHandlerManager singleton;
    private IAdsHandler adsHandler;

    private AdsHandlerManager() {
        adsHandler = new AdMobHandler();
    }

    public static AdsHandlerManager getInstance() {
        if (singleton == null) {
            synchronized (AdsHandlerManager.class) {
                if (singleton == null) {
                    singleton = new AdsHandlerManager();
                }
            }
        }
        return singleton;
    }

    @Override
    public void init(Context context) {
        if(adsHandler != null) {
            adsHandler.init(context);
        }
    }

    @Override
    public void addBannerAd(ViewGroup layout, String adUnitId) {
        if(adsHandler != null) {
            adsHandler.addBannerAd(layout, adUnitId);
        }
    }

    @Override
    public void addInterstitialAd(Context context, String adUnitId) {
        if(adsHandler != null) {
            adsHandler.addInterstitialAd(context, adUnitId);
        }
    }

    @Override
    public void showIntersitialAd(Context context, String adUnitId) {
        if (adsHandler != null) {
            adsHandler.showIntersitialAd(context, adUnitId);
        }
    }

    @Override
    public void addRewardAd(Context context, String adUnitId) {
        if (adsHandler != null) {
            adsHandler.addRewardAd(context, adUnitId);
        }
    }

    @Override
    public void showRewardAd(Context context, String adUnitId) {
        if (adsHandler != null) {
            adsHandler.showRewardAd(context, adUnitId);
        }
    }

    @Override
    public void addNativeAd(ViewGroup layout, String adUnitId) {
        if (adsHandler != null) {
            adsHandler.addNativeAd(layout, adUnitId);
        }
    }

    @Override
    public void addAppOpenAd(Application application, String adUnitId) {
        if (adsHandler != null) {
            adsHandler.addAppOpenAd(application, adUnitId);
        }
    }

    @Override
    public void onResume(Context context) {
        if(adsHandler != null) {
            adsHandler.onResume(context);
        }
    }

    @Override
    public void onPause(Context context) {
        if(adsHandler != null) {
            adsHandler.onPause(context);
        }
    }

    @Override
    public void onDestroy(Context context) {
        if(adsHandler != null) {
            adsHandler.onDestroy(context);
        }
    }
}