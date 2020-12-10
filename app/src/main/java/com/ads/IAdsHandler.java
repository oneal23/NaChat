package com.ads;

import android.app.Application;
import android.content.Context;
import android.view.ViewGroup;

public interface IAdsHandler {
    void init(Context context);

    void addBannerAd(ViewGroup layout, String adUnitId);

    void onResume(Context context);

    void onPause(Context context);

    void onDestroy(Context context);

    void addInterstitialAd(Context context, String adUnitId);

    void showIntersitialAd(Context context, String adUnitId);

    void addRewardAd(Context context, String adUnitId);

    void showRewardAd(Context context, String adUnitId);

    void addNativeAd(ViewGroup layout, String adUnitId);

    void addAppOpenAd(Application application, String adUnitId);
}
