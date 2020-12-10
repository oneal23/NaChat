package com.livesdk;

public interface ILSSelfView extends ILSView {

    void setFrontCamera(boolean frontCamera);

    boolean isFrontCamera();

    void setEnableCamera(boolean enableCamera);

    boolean isEnableCamera();
}
