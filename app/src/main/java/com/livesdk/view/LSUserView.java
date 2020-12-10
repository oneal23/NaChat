package com.livesdk.view;

import android.view.View;

import com.livesdk.ILSView;

public class LSUserView implements ILSView {
    private int index;
    private View view;
    private String userId;

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
        setUserId(null);
        setView(null);
    }
}
