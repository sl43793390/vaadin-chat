package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.RolesPermission;
import com.example.entity.RolesPermissionExample;
import com.example.entity.User;
import com.example.entity.UserExample;
import com.example.entity.UsersRole;
import com.example.entity.UsersRoleExample;
import com.example.mapper.PermissionMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.RolesPermissionMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.UsersRoleMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UsersRoleMapper userRoleMapper;
	@Autowired
	private RolesPermissionMapper rolePermissionMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	
	public User getUserById(String userId) {
		UserExample ex = new UserExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<User> selectByExample = userMapper.selectByExample(ex);
		if (null != selectByExample && !selectByExample.isEmpty()) {
			return selectByExample.get(0);
		}
		return null;
	}
	
	public UsersRole getRoleByUserId(String userId) {
		UsersRoleExample ex = new UsersRoleExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<UsersRole> selectByExample = userRoleMapper.selectByExample(ex);
		if (null != selectByExample && !selectByExample.isEmpty()) {
			return selectByExample.get(0);
		}
		return null;
	}
	
	public List<RolesPermission> getRolePermissionByRoleId(String roleId){
		RolesPermissionExample ex = new RolesPermissionExample();
		ex.createCriteria().andRoleIdEqualTo(roleId);
		List<RolesPermission> selectByExample = rolePermissionMapper.selectByExample(ex);
		return selectByExample;
	}
	
}
