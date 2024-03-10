package org.chengrong.naive.chat.domain.user.model;

import org.chengrong.naive.chat.infrastructure.common.Constants;

import java.util.Date;

public class ChatRecordInfo {
    private String userId;     // 用户ID
    private String friendId;   // 好友ID
    private String msgContent; // 消息内容
    private Integer msgType;   // 消息类型；0文字消息、1固定表情
    private Date msgDate;      // 消息时间
    private Integer talkType;  // 对话框类型；0好友、1群组

    public ChatRecordInfo() {
    }

    public ChatRecordInfo(String userId, String friendId, String msgContent, Integer msgType, Date msgDate) {
        this.userId = userId;
        this.friendId = friendId;
        this.msgContent = msgContent;
        this.msgType = msgType;
        this.msgDate = msgDate;
        this.talkType = Constants.TalkType.Friend.getCode();
    }

    public ChatRecordInfo(String userId, String friendId, String msgContent, Integer msgType, Date msgDate, Integer talkType) {
        this.userId = userId;
        this.friendId = friendId;
        this.msgContent = msgContent;
        this.msgType = msgType;
        this.msgDate = msgDate;
        this.talkType = talkType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public Integer getTalkType() {
        return talkType;
    }

    public void setTalkType(Integer talkType) {
        this.talkType = talkType;
    }
}
