package com.livesdk;

import java.util.List;

public interface ILSRoom {
    String getId();

    List<ILSUser> getUsers();

    void setCallLoginRoomOK(boolean isCallLoginRoomOK);

    void setLogined(boolean logined);

    boolean isLogined();

    boolean needLoginRoom();

    void reset();

    void setStartPreviewed(boolean isStartPreviewed);

    boolean isStartPreviewed();

    ILSUser getUser(String uid);

    boolean addUser(ILSUser user);

    boolean removeUser(String uid);
}
