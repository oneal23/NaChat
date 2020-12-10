package com.livesdk.data;

import android.text.TextUtils;

import com.livesdk.ILSRoom;

public class LSSelfUser extends LSUser {
    private ILSRoom room;

    public LSSelfUser(String id, String name) {
        super(id, name);
    }

    public ILSRoom getRoom() {
        return room;
    }

    public void setRoom(ILSRoom room) {
        this.room = room;
    }

    @Override
    public String getStreamId() {
        if (TextUtils.isEmpty(super.getStreamId()) && getRoom() != null) {
            setStreamId("meow-" + getId() + "-" + getRoom().getId());
        }
        return super.getStreamId();
    }
}
