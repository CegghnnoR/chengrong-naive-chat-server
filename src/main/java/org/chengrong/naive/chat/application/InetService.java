package org.chengrong.naive.chat.application;

import org.chengrong.naive.chat.domain.inet.model.ChannelUserInfo;
import org.chengrong.naive.chat.domain.inet.model.ChannelUserReq;
import org.chengrong.naive.chat.domain.inet.model.InetServerInfo;

import java.util.List;

/**
 * 网络信息查询
 */
public interface InetService {
    InetServerInfo queryNettyServerInfo();
    Long queryChannelUserCount(ChannelUserReq req);
    List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req);
}
