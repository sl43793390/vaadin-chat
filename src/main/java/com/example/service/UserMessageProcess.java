package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.component.util.ComponentUtil;
import com.example.entity.GroupMessage;
import com.example.entity.GroupMessageExample;
import com.example.entity.UserMessage;
import com.example.entity.UserMessageExample;
import com.example.mapper.GroupMessageMapper;
import com.example.mapper.GroupUserMapMapper;
import com.example.mapper.UserMessageMapper;

@Service
public class UserMessageProcess {

	@Autowired
	private UserMessageMapper userMessageMapper;
	@Autowired
	private GroupUserMapMapper groupUserMapper;
	@Autowired
	private GroupMessageMapper groupMessageMapper;

	public List<UserMessage> getUserMessageByUserId(String userId) {
		UserMessageExample ex = new UserMessageExample();
		ex.createCriteria().andUserIdEqualTo(ComponentUtil.getCurrentUser().getUserId())
				.andReceiveUserIdEqualTo(userId);
		ex.or().andUserIdEqualTo(userId).andReceiveUserIdEqualTo(ComponentUtil.getCurrentUser().getUserId());
		ex.setOrderByClause(" MESSAGE_ID ASC limit 100");
		List<UserMessage> selectByExample = userMessageMapper.selectByExample(ex);
		return selectByExample;
	}

	public void insertUserMessage(UserMessage message) {
		userMessageMapper.insert(message);
	}

	public Long getMaxMessageId(String userId) {
		return userMessageMapper.getMaxMessageIdByUserId(userId);
	}
	/**
	 * 获取某个组的最大ID
	 * @param groupId
	 * @return
	 */
	public Long getMaxGroupMessageIdByGroupId(String groupId) {
		return groupMessageMapper.getMaxMessageIdByGroupId(groupId);
	}
	/**
	 * 获取某个用户所有组的最大ID
	 * @param userId
	 * @return
	 */
	public Long getCurrentMaxGroupMessageId(String userId) {
		return groupMessageMapper.getMaxMessageIdByUserId(userId);
	}
	
	public List<UserMessage> getUserMessageGtCurrent(long currentMessageId){
		UserMessageExample ex = new UserMessageExample();
		ex.createCriteria().andReceiveUserIdEqualTo(ComponentUtil.getCurrentUser().getUserId()).andMessageIdGreaterThan(currentMessageId);
		ex.setOrderByClause(" DT_SEND ASC");
		List<UserMessage> selectByExample = userMessageMapper.selectByExample(ex);
		return selectByExample;
	}
	

	public List<GroupMessage> getGroupMessageGtCurrent(Long currentMaxGroupMessageId) {
		return groupMessageMapper.selectMessageOverCurrent(currentMaxGroupMessageId,ComponentUtil.getCurrentUser().getUserId());
	}
	
	public List<String> getGroupByUserId(String userId){
		List<String> selectGroupByUserId = groupUserMapper.selectGroupByUserId(userId);
		return selectGroupByUserId;
	}

	public List<GroupMessage> getGroupMessageByUserId(String groupId) {
		GroupMessageExample ex = new GroupMessageExample();
		ex.createCriteria().andGroupIdEqualTo(groupId);
		ex.setOrderByClause(" DT_SEND LIMIT 100");
		List<GroupMessage> selectByExample = groupMessageMapper.selectByExample(ex);
		return selectByExample;
	}

	public void insertGroupMessage(GroupMessage gm) {
		groupMessageMapper.insert(gm);
	}

}
