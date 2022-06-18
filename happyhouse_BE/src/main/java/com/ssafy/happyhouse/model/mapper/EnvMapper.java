package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.EnvDto;

@Mapper
public interface EnvMapper {
	List<EnvDto> find(Map<String, String> map) throws SQLException;
}
