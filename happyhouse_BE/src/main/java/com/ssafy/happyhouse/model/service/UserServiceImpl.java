package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int idCheck(String id) {
		return userMapper.idCheck(id);
	}

	@Override
	@Transactional
	public int registerUser(UserDto userDto) throws Exception {
		return userMapper.registerUser(userDto);
	}

	@Override
	public UserDto login(Map<String, String> map) throws Exception {
		return userMapper.login(map);
	}

	@Override
	@Transactional
	public int updateUserInfo(UserDto userDto, String originUserId) throws Exception {
		return userMapper.updateUser(userDto, originUserId);
	}

	@Override
	public String findPassword(String id, String name, String tel) throws Exception {
		return userMapper.findPassword(id, name, tel);
	}

	@Override
	@Transactional
	public int removeUser(String userid) throws SQLException {
		return userMapper.removeUser(userid);		
	}

	@Override
	public UserDto userInfo(String userid) throws SQLException {
		return userMapper.userInfo(userid);
	}

}
