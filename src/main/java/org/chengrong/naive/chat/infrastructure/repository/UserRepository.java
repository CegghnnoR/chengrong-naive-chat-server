package org.chengrong.naive.chat.infrastructure.repository;

import com.alibaba.fastjson2.JSON;
import org.chengrong.naive.chat.domain.user.model.LuckUserInfo;
import org.chengrong.naive.chat.domain.user.model.UserInfo;
import org.chengrong.naive.chat.domain.user.repository.IUserRepository;
import org.chengrong.naive.chat.infrastructure.dao.ITalkBoxDao;
import org.chengrong.naive.chat.infrastructure.dao.IUserDao;
import org.chengrong.naive.chat.infrastructure.dao.IUserFriendDao;
import org.chengrong.naive.chat.infrastructure.po.TalkBox;
import org.chengrong.naive.chat.infrastructure.po.User;
import org.chengrong.naive.chat.infrastructure.po.UserFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userRepository")
public class UserRepository implements IUserRepository {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserFriendDao userFriendDao;

    @Autowired
    private ITalkBoxDao talkBoxDao;
    @Override
    public String queryUserPassword(String userId) {
        return userDao.queryUserPassword(userId);
    }

    @Override
    public UserInfo queryUserInfo(String userId) {
        User user = userDao.queryUserById(userId);
        return new UserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead());
    }

    @Override
    public List<LuckUserInfo> queryFuzzyUserInfoList(String userId, String searchKey) {
        List<LuckUserInfo> luckUserInfoList = new ArrayList<>();
        List<User> userList = userDao.queryFuzzyUserList(userId, searchKey);
        System.out.println("真正的查询结果：" + JSON.toJSONString(userList));
        for (User user : userList) {
            LuckUserInfo userInfo = new LuckUserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead(), 0);
            // 查询是不是已经是好友了
            UserFriend req = new UserFriend();
            req.setUserId(userId);
            req.setUserFriendId(user.getUserId());
            UserFriend userFriend = userFriendDao.queryUserFriendById(req);
            if (userFriend != null) {
                userInfo.setStatus(2);
            }
            luckUserInfoList.add(userInfo);
        }
        return luckUserInfoList;
    }

    @Override
    public void addUserFriend(List<UserFriend> userFriendList) {
        try {
            userFriendDao.addUserFriendList(userFriendList);
        } catch (DuplicateKeyException ignored) {
        }
    }

    @Override
    public void addTalkBoxInfo(String userId, String talkId, Integer talkType) {
        try {
            if (talkBoxDao.queryTalkBox(userId, talkId) != null) {
                return;
            }
            TalkBox talkBox = new TalkBox();
            talkBox.setUserId(userId);
            talkBox.setTalkId(talkId);
            talkBox.setTalkType(talkType);
            talkBoxDao.addTalkBox(talkBox);
        } catch (DuplicateKeyException ignored) {
        }
    }

    @Override
    public void deleteUserTalk(String userId, String talkId) {
        talkBoxDao.deleteUserTalk(userId, talkId);
    }

    @Override
    public List<String> queryUserGroupsIdList(String userId) {

    }
}
