package com.livesdk.view;

import android.view.View;

import com.livesdk.ILSSelfView;

public class LSSelfView implements ILSSelfView {
    private int index = 0;
    private View view;
    private String userId;
    private boolean isFrontCamera = true;
    private boolean isEnableCamera = true;

    @Override
    public void setFrontCamera(boolean frontCamera) {
        this.isFrontCamera = frontCamera;
    }

    @Override
    public boolean isFrontCamera() {
        return isFrontCamera;
    }

    @Override
    public void setEnableCamera(boolean enableCamera) {
        this.isEnableCamera = enableCamera;
    }

    @Override
    public boolean isEnableCamera() {
        return isEnableCamera;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setUserId(String userI) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void reset() {
        setEnableCamera(true);
        setFrontCamera(true);
        setView(null);
    }
}
