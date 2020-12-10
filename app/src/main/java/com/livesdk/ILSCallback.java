package com.livesdk;

public interface ILSCallback {

    void onLSLoginRoom(boolean success);

    void onLSLoginRoomResult(boolean success);

    void onLSStopLivePublisher();

    void onLSStopLivePlayer();

    void onLSAddUser(String userID, String userName, String streamID);

    void onLSDeleteUser(String userID, String userName, String streamID);

    void onLSPublishState(boolean isPublishOK);
}
