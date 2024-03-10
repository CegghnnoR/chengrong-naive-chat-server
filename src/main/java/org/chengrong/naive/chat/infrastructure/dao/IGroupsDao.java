package org.chengrong.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.chengrong.naive.chat.infrastructure.po.Groups;

@Mapper
public interface IGroupsDao {

    Groups queryGroupsById(String groupsId);

}
