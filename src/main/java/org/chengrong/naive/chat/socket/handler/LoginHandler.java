package org.chengrong.naive.chat.socket.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.domain.user.model.GroupsInfo;
import org.chengrong.naive.chat.domain.user.model.TalkBoxInfo;
import org.chengrong.naive.chat.domain.user.model.UserFriendInfo;
import org.chengrong.naive.chat.domain.user.model.UserInfo;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.infrastructure.po.TalkBox;
import org.chengrong.naive.chat.protocol.login.LoginRequest;
import org.chengrong.naive.chat.protocol.login.LoginResponse;
import org.chengrong.naive.chat.socket.MyBizHandler;

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
        List<GroupsInfo> groupsInfoList = userService.queryUserGroupsInfoList(msg.getUserId());
        List<UserFriendInfo> userFriendInfoList = userService.queryUserFriendInfoList(msg.getUserId());

        loginResponse.setSuccess(true);

        loginResponse.setUserId(userInfo.getUserId());
        loginResponse.setUserNickName(userInfo.getUserNickName());
        loginResponse.setUserHead(userInfo.getUserHead());
        channel.writeAndFlush(loginResponse);
        // 用户信息


//        // 对话框
//        List<TalkBoxInfo> talkBoxInfos = userService.queryTalkBoxInfoList(msg.getUserId());
//        for (TalkBoxInfo talkBoxInfo : talkBoxInfos) {
//            ChatTalkDto chatTalk = new ChatTalkDto();
//            chatTalk.setTalkId(talkBoxInfo.getTalkId());
//            chatTalk.setTalkType(talkBoxInfo.getTalkType());
//            chatTalk.setTalkName(talkBoxInfo.getTalkName());
//            chatTalk.setTalkHead(talkBoxInfo.getTalkHead());
//            chatTalk.setTalkSketch(talkBoxInfo.getTalkSketch());
//            chatTalk.setTalkDate(talkBoxInfo.getTalkDate());
//            loginResponse.getChatTalkList().add(chatTalk);
//
//            // 好友；聊天信息
//            if (Constants.TalkType.Friend.getCode().equals(talkBoxInfo.getTalkType())) {
//                List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
//                List<ChatRecordInfo>
//            }
//        }

    }
}
