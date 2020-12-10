package com.livesdk;

import android.view.View;

public interface ILSView {

    void setIndex(int index);

    int getIndex();

    void setView(View view);

    View getView();

    void setUserId(String userId);

    String getUserId();

    void reset();
}
