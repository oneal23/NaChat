package com.ads.admob;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

public class AdMobInterstitialItem extends AdMobItem {
    private static final String TAG = "AdMobInterstitialItem";
    private Context context;
    private InterstitialAd interstitialAd;
    private boolean isShow = false;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void load() {
        if (interstitialAd == null || !interstitialAd.getAdUnitId().equals(adUnitId)) {
            if (interstitialAd != null) {
                interstitialAd.setAdListener(null);
            }
            interstitialAd = new InterstitialAd(getContext());
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    Log.e(TAG, "onAdClosed");
                    loadNext();
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e(TAG, "onAdFailedToLoad error=" + loadAdError.toString());
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    Log.e(TAG, "onAdLeftApplication");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    Log.e(TAG, "onAdOpened");
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e(TAG, "onAdLoaded");
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    Log.e(TAG, "onAdClicked");
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    Log.e(TAG, "onAdImpression");
                }
            });
            interstitialAd.setAdUnitId(adUnitId);
        }
        loadNext();
    }

    private void loadNext() {
        if(interstitialAd != null) {
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    @Override
    public void show() {
        isShow = true;
        showView();
    }

    private void showView() {
        if (isShow && interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    @Override
    public void destroy(Context context) {
        if (context != null && getContext() != null && context == getContext()) {
            this.adUnitId = null;
            this.context = null;
            this.interstitialAd = null;
            this.isShow = false;
        }
    }

    @Override
    public Object getObject() {
        return getContext();
    }
}
