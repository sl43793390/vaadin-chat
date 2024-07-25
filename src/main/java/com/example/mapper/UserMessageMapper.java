package com.example.mapper;

import com.example.entity.UserMessage;
import com.example.entity.UserMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMessageMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("receiveUserId") String receiveUserId, @Param("messageId") Long messageId);

    int insert(UserMessage record);

    int insertSelective(UserMessage record);

    List<UserMessage> selectByExample(UserMessageExample example);

    UserMessage selectByPrimaryKey(@Param("userId") String userId, @Param("receiveUserId") String receiveUserId, @Param("messageId") Long messageId);

    int updateByExampleSelective(@Param("record") UserMessage record, @Param("example") UserMessageExample example);

    int updateByExample(@Param("record") UserMessage record, @Param("example") UserMessageExample example);

    int updateByPrimaryKeySelective(UserMessage record);

    int updateByPrimaryKey(UserMessage record);
    
    Long getMaxMessageIdByUserId(@Param("userId") String userId);
}