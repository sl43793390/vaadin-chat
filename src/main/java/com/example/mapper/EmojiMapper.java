package com.example.mapper;

import com.example.entity.Emoji;
import com.example.entity.EmojiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmojiMapper {
    int deleteByPrimaryKey(String emoDesc);

    int insert(Emoji record);

    int insertSelective(Emoji record);

    List<Emoji> selectByExample(EmojiExample example);

    Emoji selectByPrimaryKey(String emoDesc);

    int updateByExampleSelective(@Param("record") Emoji record, @Param("example") EmojiExample example);

    int updateByExample(@Param("record") Emoji record, @Param("example") EmojiExample example);

    int updateByPrimaryKeySelective(Emoji record);

    int updateByPrimaryKey(Emoji record);
}