package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.component.util.ComponentUtil;
import com.example.mapper.EmojiMapper;
import com.example.mapper.GroupInfoMapper;
import com.example.mapper.GroupUserMapMapper;
import com.example.mapper.UserFriendMapper;
import com.example.mapper.UserMapper;

import cn.hutool.core.util.ObjectUtil;

@Service
public class UserFriendProcess {
	
	@Autowired
	private UserFriendMapper userFriendMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private EmojiMapper emojiMapper;
	@Autowired
	private GroupInfoMapper groupInfoMapper;
	@Autowired
	private GroupUserMapMapper groupUserMapper;

	public List<UserFriend> getUserFriendById(String userId) {
		UserFriendExample ex = new UserFriendExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		ex.setOrderByClause(" DT_UPDATE DESC");
		List<UserFriend> selectByExample = userFriendMapper.selectByExample(ex);
		return selectByExample;
	}

	public List<User> getUserByFrendIds(){
		List<UserFriend> userFriendById = getUserFriendById(ComponentUtil.getCurrentUserId());
		List<String> ids = userFriendById.stream().map(UserFriend::getFriendId).collect(Collectors.toList());
		UserExample ex = new UserExample();
		ex.createCriteria().andUserIdIn(ids);
		List<User> users = userMapper.selectByExample(ex);
		return users;
	}
	
	public User getUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}
	
	public GroupInfo getGroupInfoById(String groupId) {
		GroupInfo selectByPrimaryKey = groupInfoMapper.selectByPrimaryKey(groupId);
		return selectByPrimaryKey;
	}
	
	public Integer insertUserFriend(String friend) {
		UserFriend f = new UserFriend();
		f.setUserId(ComponentUtil.getCurrentUser().getUserId());
		f.setFriendId(friend);
		f.setDtCreate(new Date());
		userFriendMapper.insert(f);
		//同时插入两条数据，目前简化操作，代表好友默认同意添加
		f.setUserId(friend);
		f.setFriendId(ComponentUtil.getCurrentUser().getUserId());
		return userFriendMapper.insert(f);
	}
	
	public void deletetUserFriend(String friend) {
		//同时插入两条数据，目前简化操作，代表好友默认同意添加
		userFriendMapper.deleteByPrimaryKey(ComponentUtil.getCurrentUser().getUserId(),friend);
		userFriendMapper.deleteByPrimaryKey(friend,ComponentUtil.getCurrentUser().getUserId());
	}
	
	public List<User> searchUser(String userId){
		UserExample ex = new UserExample();
		ex.createCriteria().andUserIdLike(userId+"%");
		List<User> selectByExample = userMapper.selectByExample(ex);
		return selectByExample;
	}
	
	public List<GroupInfo> searchGroup(String groupName){
		GroupInfoExample ex = new GroupInfoExample();
		ex.createCriteria().andGroupNameLike(groupName+"%");
		List<GroupInfo> selectByExample = groupInfoMapper.selectByExample(ex);
		return selectByExample;
	}
	/**
	 * 存在返回true
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public boolean checkUserFriendExists(String userId,String friendId) {
		UserFriend selectByPrimaryKey = userFriendMapper.selectByPrimaryKey(userId, friendId);
		if (ObjectUtil.isNotEmpty(selectByPrimaryKey)) {
			return true;
		}
		return false;
	}
	//判断群主
	public boolean checkIsGroupOwner(String groupId) {
		GroupInfo selectByPrimaryKey = groupInfoMapper.selectByPrimaryKey(groupId);
		if (selectByPrimaryKey.getGroupOwner() != null && selectByPrimaryKey.getGroupOwner().equals(ComponentUtil.getCurrentUserId())) {
			return true;
		}
		return false;
	}
	
//	解散群
	public boolean dissolveGroup(String groupId) {
		groupInfoMapper.deleteByPrimaryKey(groupId);
		GroupUserMapExample ex = new GroupUserMapExample();
		ex.createCriteria().andGroupIdEqualTo(groupId);
		int deleteByExample = groupUserMapper.deleteByExample(ex);
		return deleteByExample > 0;
	}

//	退出群
	public boolean leaveGroup(String groupId,String userId) {
		int deleteByPrimaryKey = groupUserMapper.deleteByPrimaryKey(userId, groupId);
		return deleteByPrimaryKey > 0;
	}

	public void updateUserFriend(UserFriend userFriend) {
		userFriendMapper.updateByPrimaryKeySelective(userFriend);
	}

	public List<User> getUserInGroup(String groupId){
		GroupUserMapExample ex = new GroupUserMapExample();
		ex.createCriteria().andGroupIdEqualTo(groupId);
		List<GroupUserMap> groupUserMaps = groupUserMapper.selectByExample(ex);
		List<String> collect = groupUserMaps.stream().map(groupUserMap -> groupUserMap.getUserId()).collect(Collectors.toList());
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdIn(collect);
		List<User> users = userMapper.selectByExample(userExample);
		return users;
	}
	
	public List<Emoji> getAllEmoji(){
		return emojiMapper.selectByExample(new EmojiExample());
	}

	public void createGroup(String value, Set<User> selectedItems) {
		GroupInfo info = new GroupInfo();
		info.setDtCreate(new Date());
		String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
		info.setGroupId(snowflakeNextIdStr);
		info.setGroupName(value);
		info.setGroupOwner(ComponentUtil.getCurrentUserId());
		groupInfoMapper.insert(info);
		for (User u : selectedItems) {
			GroupUserMap gu = new GroupUserMap();
			gu.setGroupId(snowflakeNextIdStr);
			gu.setUserId(u.getUserId());
			gu.setDtCreate(new Date());
			groupUserMapper.insert(gu);
		}
		GroupUserMap gu = new GroupUserMap();
		gu.setGroupId(snowflakeNextIdStr);
		gu.setUserId(ComponentUtil.getCurrentUserId());
		gu.setDtCreate(new Date());
		groupUserMapper.insert(gu);
	}
	public List<String> getGroupByUserId(String userId){
		List<String> selectGroupByUserId = groupUserMapper.selectGroupByUserId(userId);
		return selectGroupByUserId;
	}
	public List<GroupInfo> getGroupInfoByGroupIds(List<String> ids){
		if (CollectionUtil.isEmpty(ids)){
			return null;
		}
		GroupInfoExample ex = new GroupInfoExample();
		ex.createCriteria().andGroupIdIn(ids);
		List<GroupInfo> groupInfos = groupInfoMapper.selectByExample(ex);
		return groupInfos;
	}
	public List<GroupInfo> getGroupInfoByUserId(){
		List<String> groupByUserId = getGroupByUserId(ComponentUtil.getCurrentUserId());
		return getGroupInfoByGroupIds(groupByUserId);
	}

	public boolean transferGroupOwner(GroupInfo currentSelectedGroup, Set<User> selectedItems) {
		try {
			if (null != currentSelectedGroup){
				for (User u : selectedItems) {
					currentSelectedGroup.setGroupOwner(u.getUserId());
					groupInfoMapper.updateByPrimaryKey(currentSelectedGroup);
					break;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void updateGroupInfo(GroupInfo currentSelectedGroup, String name, String announce) {
		currentSelectedGroup.setGroupName(name);
		currentSelectedGroup.setGroupAnnouncement(announce);
		groupInfoMapper.updateByPrimaryKey(currentSelectedGroup);
	}
}
