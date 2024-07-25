package com.example.mapper;

import com.example.entity.GroupInfo;
import com.example.entity.GroupInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupInfoMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(GroupInfo record);

    int insertSelective(GroupInfo record);

    List<GroupInfo> selectByExample(GroupInfoExample example);

    GroupInfo selectByPrimaryKey(String groupId);

    int updateByExampleSelective(@Param("record") GroupInfo record, @Param("example") GroupInfoExample example);

    int updateByExample(@Param("record") GroupInfo record, @Param("example") GroupInfoExample example);

    int updateByPrimaryKeySelective(GroupInfo record);

    int updateByPrimaryKey(GroupInfo record);
}