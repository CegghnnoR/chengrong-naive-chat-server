package org.chengrong.naive.chat.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.domain.user.model.ChatRecordInfo;
import org.chengrong.naive.chat.domain.user.model.UserInfo;
import org.chengrong.naive.chat.infrastructure.common.Constants;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.protocol.msg.MsgGroupRequest;
import org.chengrong.naive.chat.protocol.msg.MsgGroupResponse;
import org.chengrong.naive.chat.socket.MyBizHandler;

public class MsgGroupHandler extends MyBizHandler<MsgGroupRequest> {
    public MsgGroupHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, MsgGroupRequest msg) {
        ChannelGroup channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        if (channelGroup == null) {
            SocketChannelUtil.addChannelGroup(msg.getTalkId(), channel);
            channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        }
        // 异步写库
        userService.asyncAppendChatRecord(new ChatRecordInfo(msg.getUserID(),
                msg.getTalkId(),
                msg.getMsgText(), msg.getMsgType(),
                msg.getMsgDate(), Constants.TalkType.Group.getCode()));
        // 群发消息
        UserInfo userInfo = userService.queryUserInfo(msg.getUserID());
        MsgGroupResponse msgGroupResponse = new MsgGroupResponse();
        msgGroupResponse.setTalkId(msg.getTalkId());
        msgGroupResponse.setUserId(msg.getUserID());
        msgGroupResponse.setUserNickName(userInfo.getUserNickName());
        msgGroupResponse.setUserHead(userInfo.getUserHead());
        msgGroupResponse.setMsg(msg.getMsgText());
        msgGroupResponse.setMsgType(msg.getMsgType());
        msgGroupResponse.setMsgDate(msg.getMsgDate());

        System.out.println(msg.getMsgDate());

        channelGroup.writeAndFlush(msgGroupResponse);
    }
}
