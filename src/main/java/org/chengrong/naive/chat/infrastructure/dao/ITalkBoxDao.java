package org.chengrong.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.chengrong.naive.chat.infrastructure.po.TalkBox;

import java.util.List;

@Mapper
public interface ITalkBoxDao {

    List<TalkBox> queryTalkBoxList(String userId);

    void addTalkBox(TalkBox talkBox);

    void deleteUserTalk(String userId, String talkId);

    TalkBox queryTalkBox(String userId, String talkId);

    List<String> queryTalkBoxGroupsIdList(String userId);
}
