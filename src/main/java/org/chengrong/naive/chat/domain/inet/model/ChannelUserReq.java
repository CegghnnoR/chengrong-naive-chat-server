package org.chengrong.naive.chat.domain.inet.model;

import org.chengrong.naive.chat.infrastructure.common.PageReq;

public class ChannelUserReq extends PageReq {
    private String userId;
    private String userNickName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
