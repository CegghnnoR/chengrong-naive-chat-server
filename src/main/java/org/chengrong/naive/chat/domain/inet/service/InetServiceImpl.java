package org.chengrong.naive.chat.domain.inet.service;

import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import org.chengrong.naive.chat.application.InetService;
import org.chengrong.naive.chat.domain.inet.model.ChannelUserInfo;
import org.chengrong.naive.chat.domain.inet.model.ChannelUserReq;
import org.chengrong.naive.chat.domain.inet.model.InetServerInfo;
import org.chengrong.naive.chat.domain.inet.repository.IInetRepository;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.socket.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inetService")
public class InetServiceImpl implements InetService {
    @Value("${netty.ip}")
    private String ip;
    @Value("${netty.port}")
    private int port;

    @Resource
    private NettyServer nettyServer;
    @Resource
    private IInetRepository inetRepository;

    @Override
    public InetServerInfo queryNettyServerInfo() {
        InetServerInfo inetServerInfo = new InetServerInfo();
        inetServerInfo.setIp(ip);
        inetServerInfo.setPort(port);
        inetServerInfo.setStatus(nettyServer.channel().isActive());
        return inetServerInfo;
    }

    @Override
    public Long queryChannelUserCount(ChannelUserReq req) {
        return inetRepository.queryChannelUserCount(req);
    }

    @Override
    public List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req) {
        List<ChannelUserInfo> channelUserInfoList = inetRepository.queryChannelUserList(req);
        for (ChannelUserInfo channelUserInfo : channelUserInfoList) {
            Channel channel = SocketChannelUtil.getChannel(channelUserInfo.getUserId());
            channelUserInfo.setStatus(channel != null && channel.isActive());
        }
        return channelUserInfoList;
    }
}
