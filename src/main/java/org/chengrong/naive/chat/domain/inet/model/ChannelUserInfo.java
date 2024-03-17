package org.chengrong.naive.chat.domain.inet.model;

public class ChannelUserInfo {
    private String userId;        // 用户ID
    private String userNickName;  // 用户昵称
    private String userHead;      // 用户头像
    private boolean status;       // 状态【true在线 false不在线】

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

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
