package org.chengrong.naive.chat.socket.handler;

import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.protocol.talk.DelTalkRequest;
import org.chengrong.naive.chat.socket.MyBizHandler;

public class DelTalkHandler extends MyBizHandler<DelTalkRequest> {
    public DelTalkHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, DelTalkRequest msg) {
        userService.deleteUserTalk(msg.getUserId(), msg.getTalkId());
    }
}
