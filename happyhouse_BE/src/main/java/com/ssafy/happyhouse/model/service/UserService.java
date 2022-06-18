package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.happyhouse.model.UserDto;



public interface UserService {
	int idCheck(String id);
	int registerUser(UserDto userDto) throws Exception;
	int updateUserInfo(UserDto userDto, String originUserId) throws Exception;
	int removeUser(String userid) throws SQLException;
	UserDto login(Map<String, String> map) throws Exception;
	UserDto userInfo(String userid) throws SQLException;

	String findPassword(String id, String name, String tel) throws Exception;
}
