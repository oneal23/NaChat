package com.ads.admob;

import android.content.Context;

public abstract class AdMobItem {
    protected String adUnitId;

    public void setAdUnitId(String adUnitId) {
        this.adUnitId = adUnitId;
    }

    public String getAdUnitId() {
        return adUnitId;
    }

    public void show() {

    }

    public void load() {

    }

    public void resume(Context context) {

    }

    public void pause(Context context) {

    }

    public void destroy(Context context) {

    }

    public Object getObject() {
        return null;
    }
}
