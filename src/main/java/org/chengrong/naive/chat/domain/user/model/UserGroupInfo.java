package org.chengrong.naive.chat.domain.user.model;

public class UserGroupInfo {
    private String userId;       //用户ID
    private String userNickName; //用户昵称
    private String userHead;     //用户头像

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
}
