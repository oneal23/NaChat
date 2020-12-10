package com.livesdk;

import android.content.Context;
import android.view.View;

public interface ILiveSdk {
    void init(Context context, String userId, String userName);

    void destory();

    void setLSCallback(ILSCallback lsCallback);

    void loginRoom(String roomId);

    void logoutRoom();

    boolean startPreview(ILSSelfView view);

    void stopPreview();

    void resetPreviewView();

    void startPublish(String streamId);

    void stopPublish();

    void startPlayStream(String streamId, View view);

    void stopPlayStream(String streamId);

}
