package org.chengrong.naive.chat.domain.user.repository;

import org.chengrong.naive.chat.domain.user.model.LuckUserInfo;
import org.chengrong.naive.chat.domain.user.model.UserInfo;
import org.chengrong.naive.chat.infrastructure.po.UserFriend;

import java.util.List;

public interface IUserRepository {
    String queryUserPassword(String userId);

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfo queryUserInfo(String userId);

    /**
     * 模糊查询用户
     *
     * @param userId 用户ID
     * @param searchKey 用户名，用户ID
     * @return <10个用户集合
     */
    List<LuckUserInfo> queryFuzzyUserInfoList(String userId, String searchKey);

    /**
     * 添加好友到数据库中
     *
     * @param userFriendList 好友集合
     */
    void addUserFriend(List<UserFriend> userFriendList);

    /**
     * 添加对话框
     *
     * @param userId 用户ID
     * @param talkId 好友ID
     * @param talkType 对话框类型【0好友、1群组】
     */
    void addTalkBoxInfo(String userId, String talkId, Integer talkType);

    /**
     * 删除对话框
     *
     * @param userId 用户ID
     * @param talkId 对话框ID
     */
    void deleteUserTalk(String userId, String talkId);

    /**
     * 查询用户群组ID集合
     *
     * @param userId 用户ID
     * @return 用户群组ID集合
     */
    List<String> queryUserGroupsIdList(String userId);
}
