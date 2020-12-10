package com.livesdk.sdk;

import android.content.Context;
import android.view.View;

import com.livesdk.ILSCallback;
import com.livesdk.ILSSelfView;
import com.livesdk.ILiveSdk;
import com.livesdk.sdk.zegoexp.ZegoEESdk;
import com.livesdk.utils.Logger;

public class LiveSdk {

    private static volatile LiveSdk singleton;

    private ILiveSdk liveSdk;

    private LiveSdk() {
        liveSdk = new ZegoEESdk();
    }

    public static LiveSdk getInstance() {
        if (singleton == null) {
            synchronized (LiveSdk.class) {
                if (singleton == null) {
                    singleton = new LiveSdk();
                }
            }
        }
        return singleton;
    }

    private static ILiveSdk getLiveSdk() {
        return getInstance().liveSdk;
    }

    private static void setIsDebug(boolean isDebug) {
        Logger.setIsDebug(isDebug);
    }

    public static void init(Context context, String userId, String userName) {
        setIsDebug(true);
        if (getLiveSdk() != null) {
            getLiveSdk().init(context, userId, userName);
        }
    }

    public static void destory() {
        if (getLiveSdk() != null) {
            getLiveSdk().destory();
        }
    }

    public static void setLSCallBack(ILSCallback lsCallBack) {
        if (getLiveSdk() != null) {
            getLiveSdk().setLSCallback(lsCallBack);
        }
    }

    public static void loginRoom(String roomId) {
        if (getLiveSdk() != null) {
            getLiveSdk().loginRoom(roomId);
        }
    }

    public static boolean startPreview(ILSSelfView view) {
        if (getLiveSdk() != null) {
            getLiveSdk().startPreview(view);
        }
        return false;
    }

    public static void stopPreview() {
        if (getLiveSdk() != null) {
            getLiveSdk().stopPreview();
        }
    }

    public static void resetPreviewView() {
        if (getLiveSdk() != null) {
            getLiveSdk().resetPreviewView();
        }
    }

    public static void logoutRoom() {
        if (getLiveSdk() != null) {
            getLiveSdk().logoutRoom();
        }
    }

    public static void startPublish(String streamId) {
        if (getLiveSdk() != null) {
            getLiveSdk().startPublish(streamId);
        }
    }

    public static void stopPublish() {
        if (getLiveSdk() != null) {
            getLiveSdk().stopPublish();
        }
    }

    public static void startPlayStream(String streamId, View view) {
        if (getLiveSdk() != null) {
            getLiveSdk().startPlayStream(streamId, view);
        }
    }

    public static void stopPlayStream(String streamId) {
        if (getLiveSdk() != null) {
            getLiveSdk().stopPlayStream(streamId);
        }
    }
}