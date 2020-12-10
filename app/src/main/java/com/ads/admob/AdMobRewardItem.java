package com.ads.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdMobRewardItem extends AdMobItem {
    private static final String TAG = "AdMobRewardItem";
    private Context context;
    private RewardedAd rewardedAd;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void show() {
        if (rewardedAd != null && rewardedAd.isLoaded()) {
            rewardedAd.show((Activity) getContext(), new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    Log.e(TAG, "onRewardedAdOpened");
                }

                @Override
                public void onRewardedAdClosed() {
                    Log.e(TAG, "onRewardedAdClosed");
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    Log.e(TAG, "onRewardedAdFailedToShow error=" + adError.toString());
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.e(TAG, "onUserEarnedReward item=" + rewardItem.toString());
                }
            });
        }
    }

    @Override
    public void load() {
        rewardedAd = new RewardedAd(getContext(), getAdUnitId());
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                Log.e(TAG, "onRewardedAdLoaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                Log.e(TAG, "onRewardedAdFailedToLoad error=" + loadAdError.toString());
            }
        });
    }

    @Override
    public void resume(Context context) {
        super.resume(context);
    }

    @Override
    public void pause(Context context) {
        super.pause(context);
    }

    @Override
    public void destroy(Context context) {
        if (context != null && getContext() != null && context == getContext()) {
            this.adUnitId = null;
            this.context = null;
            this.rewardedAd = null;
        }
    }

    @Override
    public Object getObject() {
        return getContext();
    }
}
