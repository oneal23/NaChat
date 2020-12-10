package com.livesdk.data;

import com.livesdk.ILSRoom;
import com.livesdk.ILSUser;

import java.util.LinkedList;
import java.util.List;

public class LSRoom implements ILSRoom {

    private String id;

    private List<ILSUser> users;

    private boolean isCallLoginRoomOK = false;

    private boolean isLogined = false;

    private boolean isStartPreviewed = false;

    public LSRoom(String id) {
        this.id = id;
        users = new LinkedList<ILSUser>();
    }

    public boolean isCallLoginRoomOK() {
        return isCallLoginRoomOK;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<ILSUser> getUsers() {
        return users;
    }

    @Override
    public void setCallLoginRoomOK(boolean isCallLoginRoomOK) {
        this.isCallLoginRoomOK = isCallLoginRoomOK;
    }

    @Override
    public void setLogined(boolean logined) {
        this.isLogined = logined;
    }

    @Override
    public boolean isLogined() {
        return isLogined;
    }

    @Override
    public boolean needLoginRoom() {
        return !isLogined;
    }

    @Override
    public void reset() {
        setCallLoginRoomOK(false);
        setLogined(false);
        setStartPreviewed(false);
        users.clear();
    }

    @Override
    public void setStartPreviewed(boolean isStartPreviewed) {
        this.isStartPreviewed = isStartPreviewed;
    }

    @Override
    public boolean isStartPreviewed() {
        return isStartPreviewed;
    }

    @Override
    public ILSUser getUser(String uid) {
        for (ILSUser user : users) {
            if (user.getId().equals(uid)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(ILSUser user) {
        if (user == null) {
            return false;
        }

        if (getUser(user.getId()) != null) {
            return false;
        }
        return users.add(user);
    }

    @Override
    public boolean removeUser(String uid) {
        ILSUser user = getUser(uid);
        if (user != null) {
            return users.remove(user);
        }
        return false;
    }
}
