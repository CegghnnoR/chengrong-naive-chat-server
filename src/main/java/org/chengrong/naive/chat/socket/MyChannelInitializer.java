package org.chengrong.naive.chat.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.codec.ObjDecoder;
import org.chengrong.naive.chat.codec.ObjEncoder;
import org.chengrong.naive.chat.socket.handler.*;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    protected UserService userService;

    public MyChannelInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 对象传输处理[解码]
        ch.pipeline().addLast(new ObjDecoder());
        // 添加自定义的接收数据实现方法
        ch.pipeline().addLast(new LoginHandler(userService));
        ch.pipeline().addLast(new SearchFriendHandler(userService));
        ch.pipeline().addLast(new AddFriendHandler(userService));
        ch.pipeline().addLast(new TalkNoticeHandler(userService));
        ch.pipeline().addLast(new DelTalkHandler(userService));
        ch.pipeline().addLast(new MsgHandler(userService));
        ch.pipeline().addLast(new MsgGroupHandler(userService));
        // 对象传输处理[编码]
        ch.pipeline().addLast(new ObjEncoder());
    }
}
