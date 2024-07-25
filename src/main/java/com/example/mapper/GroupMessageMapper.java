package com.example.mapper;

import com.example.entity.GroupMessage;
import com.example.entity.GroupMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupMessageMapper {
    int deleteByPrimaryKey(@Param("groupId") String groupId, @Param("messageId") Long messageId);

    int insert(GroupMessage record);

    int insertSelective(GroupMessage record);

    List<GroupMessage> selectByExample(GroupMessageExample example);

    GroupMessage selectByPrimaryKey(@Param("groupId") String groupId, @Param("messageId") Long messageId);

    int updateByExampleSelective(@Param("record") GroupMessage record, @Param("example") GroupMessageExample example);

    int updateByExample(@Param("record") GroupMessage record, @Param("example") GroupMessageExample example);

    int updateByPrimaryKeySelective(GroupMessage record);

    int updateByPrimaryKey(GroupMessage record);

	Long getMaxMessageIdByGroupId(@Param("groupId") String groupId);

	Long getMaxMessageIdByUserId(@Param("userId") String userId);

	List<GroupMessage> selectMessageOverCurrent(@Param("groupId")Long currentMaxGroupMessageId, @Param("userId")String userId);
}