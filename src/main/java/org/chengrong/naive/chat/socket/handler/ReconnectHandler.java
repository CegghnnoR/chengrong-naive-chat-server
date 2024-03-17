package org.chengrong.naive.chat.socket.handler;

import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.protocol.login.ReconnectRequest;
import org.chengrong.naive.chat.socket.MyBizHandler;

import java.util.List;

public class ReconnectHandler extends MyBizHandler<ReconnectRequest> {
    public ReconnectHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, ReconnectRequest msg) {
        logger.info("客户端断线重连处理。userId: {}", msg.getUserId());
        // 添加用户Channel
        SocketChannelUtil.removeUserChannelByUserId(msg.getUserId());
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        // 添加群组Channel
        List<String> groupsIdList = userService.queryTalkBoxGroupsIdList(msg.getUserId());
        for (String groupsId : groupsIdList) {
            SocketChannelUtil.addChannelGroup(groupsId, channel);
        }
    }
}
