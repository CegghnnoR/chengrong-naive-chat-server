package org.chengrong.naive.chat.socket.handler;

import io.netty.channel.Channel;
import org.chengrong.naive.chat.application.UserService;
import org.chengrong.naive.chat.domain.user.model.UserInfo;
import org.chengrong.naive.chat.infrastructure.common.SocketChannelUtil;
import org.chengrong.naive.chat.infrastructure.po.UserFriend;
import org.chengrong.naive.chat.protocol.friend.AddFriendRequest;
import org.chengrong.naive.chat.protocol.friend.AddFriendResponse;
import org.chengrong.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;

public class AddFriendHandler extends MyBizHandler<AddFriendRequest> {
    public AddFriendHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, AddFriendRequest msg) {
        // 添加好友到数据库中[a->b b->A]
        List<UserFriend> userFriendList = new ArrayList<>();
        userFriendList.add(new UserFriend(msg.getUserId(), msg.getFriendId()));
        userFriendList.add(new UserFriend(msg.getFriendId(), msg.getUserId()));
        userService.addUserFriend(userFriendList);
        // 推送好友添加完成 A
        UserInfo userInfo = userService.queryUserInfo(msg.getFriendId());
        channel.writeAndFlush(new AddFriendResponse(userInfo.getUserId(), userInfo.getUserNickName(), userInfo.getUserHead()));
        // 推送好友添加完成 B
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if (friendChannel == null) {
            return;
        }
        UserInfo friendInfo = userService.queryUserInfo(msg.getUserId());
        friendChannel.writeAndFlush(new AddFriendResponse(friendInfo.getUserId(), friendInfo.getUserNickName(), friendInfo.getUserHead()));
    }
}
