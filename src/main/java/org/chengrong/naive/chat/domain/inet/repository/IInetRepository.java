package org.chengrong.naive.chat.domain.inet.repository;

import org.chengrong.naive.chat.domain.inet.model.ChannelUserInfo;
import org.chengrong.naive.chat.domain.inet.model.ChannelUserReq;

import java.util.List;

public interface IInetRepository {
    Long queryChannelUserCount(ChannelUserReq req);

    List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req);
}
