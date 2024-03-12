package org.chengrong.naive.chat.socket.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.domain.user.model.LuckUserInfo;
import org.chengrong.naive.chat.protocol.friend.SearchFriendRequest;
import org.chengrong.naive.chat.protocol.friend.SearchFriendResponse;
import org.chengrong.naive.chat.protocol.friend.dto.UserDto;
import org.chengrong.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendHandler extends MyBizHandler<SearchFriendRequest> {
    public SearchFriendHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, SearchFriendRequest msg) {
        logger.info("搜索好友请求处理：{}", JSON.toJSONString(msg));
        List<UserDto> userDtoList = new ArrayList<>();
        List<LuckUserInfo> userInfoList = userService.queryFuzzyUserInfoList(msg.getUserId(), msg.getSearchKey());
        logger.info("搜索结果:{}", JSON.toJSONString(userInfoList));
        for (LuckUserInfo userInfo : userInfoList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(userInfo.getUserId());
            userDto.setUserNickName(userInfo.getUserNickName());
            userDto.setUserHead(userInfo.getUserHead());
            userDto.setStatus(userInfo.getStatus());
            userDtoList.add(userDto);
        }
        logger.info("userDtoList: {}", JSON.toJSONString(userDtoList));
        SearchFriendResponse response = new SearchFriendResponse();
        response.setList(userDtoList);
        channel.writeAndFlush(response);
    }
}
