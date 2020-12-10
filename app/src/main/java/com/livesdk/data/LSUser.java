package com.livesdk.data;

import com.livesdk.ILSUser;

public class LSUser implements ILSUser {
    private String id;
    private String name;
    private String streamId;
    private int role;

    public LSUser(String id) {
        this(id, "");
    }

    public LSUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return getName();
    }

    @Override
    public String getStreamId() {
        return streamId;
    }

    @Override
    public int getRole() {
        return role;
    }

    @Override
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean isCanPublish() {
        return role != Audience;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public static class Builder {
        private String id;
        private String name;
        private String streamId;
        private int role;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setStreamId(String streamId) {
            this.streamId = streamId;
            return this;
        }

        public Builder setRole(int role) {
            this.role = role;
            return this;
        }

        public ILSUser build() {
            LSUser user = new LSUser(id, name);
            user.setStreamId(streamId);
            user.setRole(role);
            return user;
        }
    }
}
