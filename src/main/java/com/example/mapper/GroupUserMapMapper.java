package com.example.mapper;

import com.example.entity.GroupUserMap;
import com.example.entity.GroupUserMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupUserMapMapper {
    int deleteByExample(GroupUserMapExample example);

    int deleteByPrimaryKey(@Param("userId") String userId, @Param("groupId") String groupId);

    int insert(GroupUserMap record);

    int insertSelective(GroupUserMap record);

    List<GroupUserMap> selectByExample(GroupUserMapExample example);

    GroupUserMap selectByPrimaryKey(@Param("userId") String userId, @Param("groupId") String groupId);

    int updateByExampleSelective(@Param("record") GroupUserMap record, @Param("example") GroupUserMapExample example);

    int updateByExample(@Param("record") GroupUserMap record, @Param("example") GroupUserMapExample example);

    int updateByPrimaryKeySelective(GroupUserMap record);

    int updateByPrimaryKey(GroupUserMap record);
    List<String> selectGroupByUserId(@Param("userId")String userId);
}