package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.UserDto;


@Mapper
public interface UserMapper {
	
	int idCheck(String id);
	
	UserDto login(Map<String, String> map) throws SQLException;
	UserDto userInfo(String userid) throws SQLException;
	
	int registerUser(UserDto userDto) throws SQLException;
	int updateUser(@Param("user") UserDto userDto, @Param("ori-id") String originUserId) throws SQLException;
	int removeUser(String userid) throws SQLException;
	
	String findPassword(@Param("id") String id, @Param("name") String name, @Param("tel") String tel) throws SQLException;

	
}
