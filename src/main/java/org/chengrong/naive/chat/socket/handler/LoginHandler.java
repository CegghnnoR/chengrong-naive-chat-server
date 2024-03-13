package org.chengrong.naive.chat.socket.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.domain.user.model.*;
import org.chengrong.naive.chat.infrastructure.common.Constants;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.protocol.login.LoginRequest;
import org.chengrong.naive.chat.protocol.login.LoginResponse;
import org.chengrong.naive.chat.protocol.login.dto.ChatRecordDto;
import org.chengrong.naive.chat.protocol.login.dto.ChatTalkDto;
import org.chengrong.naive.chat.protocol.login.dto.GroupsDto;
import org.chengrong.naive.chat.protocol.login.dto.UserFriendDto;
import org.chengrong.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;


public class LoginHandler extends MyBizHandler<LoginRequest> {
    public LoginHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, LoginRequest msg) {
        logger.info("登录请求处理：{}", JSON.toJSONString(msg));
        boolean auth = userService.checkAuth(msg.getUserId(), msg.getUserPassword());
        // 登录失败
        if (!auth) {
            // 传输失败信息
            channel.writeAndFlush(new LoginResponse(false));
            return;
        }
        // 登录成功，绑定Channel
        // 绑定用户ID
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        // 绑定群组
        List<String> groupsIdList = userService.queryUserGroupsIdList(msg.getUserId());
        logger.info("绑定群组:{}", JSON.toJSONString(groupsIdList));
        for (String groupId : groupsIdList) {
            SocketChannelUtil.addChannelGroup(groupId, channel);
        }
        // 反馈信息
        // 组装信息包
        LoginResponse loginResponse = new LoginResponse();
        // 用户信息
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        // 对话框
        List<TalkBoxInfo> talkBoxInfoList = userService.queryTalkBoxInfoList(msg.getUserId());
        for (TalkBoxInfo talkBoxInfo : talkBoxInfoList) {
            ChatTalkDto chatTalk = new ChatTalkDto();
            chatTalk.setTalkId(talkBoxInfo.getTalkId());
            chatTalk.setTalkType(talkBoxInfo.getTalkType());
            chatTalk.setTalkName(talkBoxInfo.getTalkName());
            chatTalk.setTalkHead(talkBoxInfo.getTalkHead());
            chatTalk.setTalkSketch(talkBoxInfo.getTalkSketch());
            chatTalk.setTalkDate(talkBoxInfo.getTalkDate());
            loginResponse.getChatTalkList().add(chatTalk);


            if (Constants.TalkType.Friend.getCode().equals(talkBoxInfo.getTalkType())) {
                // 好友；聊天信息
                List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), Constants.TalkType.Friend.getCode());
                // 把 ChatRecordInfo 转成 ChatRecordDto
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    chatRecordDto.setTalkId(talkBoxInfo.getTalkId());
                    boolean msgType = msg.getUserId().equals(chatRecordInfo.getUserId());

                    if (msgType) {
                        // 自己发的信息
                        chatRecordDto.setUserId(chatRecordInfo.getUserId());
                        chatRecordDto.setMsgUserType(0);
                    } else {
                        // 好友发的信息
                        chatRecordDto.setUserId(chatRecordInfo.getFriendId());
                        chatRecordDto.setMsgUserType(1);
                    }

                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    chatRecordDtoList.add(chatRecordDto);
                }
                chatTalk.setChatRecordList(chatRecordDtoList);
            } else if (Constants.TalkType.Group.getCode().equals(talkBoxInfo.getTalkType())) {
                // 群组；聊天信息
                List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), Constants.TalkType.Group.getCode());
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    UserInfo memberInfo = userService.queryUserInfo(chatRecordInfo.getUserId());
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    chatRecordDto.setTalkId(talkBoxInfo.getTalkId());
                    chatRecordDto.setUserId(memberInfo.getUserId());
                    chatRecordDto.setUserNickName(memberInfo.getUserNickName());
                    chatRecordDto.setUserHead(memberInfo.getUserHead());
                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    boolean msgType = msg.getUserId().equals(chatRecordInfo.getUserId());
                    chatRecordDto.setMsgUserType(msgType ? 0 : 1);
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDtoList.add(chatRecordDto);
                }
                chatTalk.setChatRecordList(chatRecordDtoList);
            }
        }
        // 群组
        List<GroupsInfo> groupsInfoList = userService.queryUserGroupInfoList(msg.getUserId());
        for (GroupsInfo groupsInfo : groupsInfoList) {
            GroupsDto groups = new GroupsDto();
            groups.setGroupId(groupsInfo.getGroupId());
            groups.setGroupName(groupsInfo.getGroupName());
            groups.setGroupHead(groupsInfo.getGroupHead());
            loginResponse.getGroupsList().add(groups);
        }
        // 好友
        List<UserFriendInfo> userFriendInfoList = userService.queryUserFriendInfoList(msg.getUserId());
        for (UserFriendInfo userFriendInfo : userFriendInfoList) {
            UserFriendDto userFriend = new UserFriendDto();
            userFriend.setFriendId(userFriendInfo.getFriendId());
            userFriend.setFriendName(userFriendInfo.getFriendName());
            userFriend.setFriendHead(userFriendInfo.getFriendHead());
            loginResponse.getUserFriendList().add(userFriend);
        }
        loginResponse.setSuccess(true);
        loginResponse.setUserId(userInfo.getUserId());
        loginResponse.setUserNickName(userInfo.getUserNickName());
        loginResponse.setUserHead(userInfo.getUserHead());
        channel.writeAndFlush(loginResponse);
    }
}
