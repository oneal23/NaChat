package com.ads.admob;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class AdMobBannerItem extends AdMobItem{
    private static final String TAG = "AdMobBannerItem";
    protected ViewGroup layout;
    private AdView adView;
    public AdView getAdView() {
        return adView;
    }

    public void setLayout(ViewGroup layout) {
        this.layout = layout;
    }

    public ViewGroup getLayout() {
        return layout;
    }

    public Context getContext() {
        if(this.layout != null) {
            return this.layout.getContext();
        }
        return null;
    }

    private AdSize getAdSize() {
        Activity activity = (Activity) getContext();
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    @Override
    public void load() {
        adView = new AdView(this.getContext());
        adView.setAdSize(getAdSize());
        adView.setAdUnitId(this.adUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.e(TAG, "onAdClosed");
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
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        getLayout().addView(adView);
    }

    @Override
    public void resume(Context context) {
        if (context != null && getContext() != null && context == getContext() && getAdView() != null) {
            getAdView().resume();
        }
    }

    @Override
    public void pause(Context context) {
        if (context != null && getContext() != null && context == getContext() && getAdView() != null) {
            getAdView().pause();
        }
    }

    @Override
    public void destroy(Context context) {
        if (context != null && getContext() != null && context == getContext() && getAdView() != null) {
            getAdView().destroy();
            getAdView().setAdListener(null);
            getLayout().removeView(getAdView());
            this.adUnitId = null;
            this.adView = null;
            this.layout = null;
        }
    }

    @Override
    public Object getObject() {
        return getLayout();
    }
}
