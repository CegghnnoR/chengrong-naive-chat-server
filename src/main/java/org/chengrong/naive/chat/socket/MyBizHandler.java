package org.chengrong.naive.chat.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.chengrong.naive.chat.application.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MyBizHandler<T> extends SimpleChannelInboundHandler<T> {
    protected Logger logger = LoggerFactory.getLogger(MyBizHandler.class);

    protected UserService userService;

    public MyBizHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        channelRead(ctx.channel(), msg);
    }

    public abstract void channelRead(Channel channel, T msg);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("客户端连接通知：{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

    }
}
