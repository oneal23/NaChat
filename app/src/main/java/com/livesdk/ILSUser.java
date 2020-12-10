package com.livesdk;

public interface ILSUser {
    int Audience = 0; //观众
    int Anchor = 1; //主播
    int Guest = 2; //嘉宾

    String getId();

    String getName();

    String getStreamId();

    int getRole();

    void setRole(int role);

    boolean isCanPublish();

}
