package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.LikeDto;
import com.ssafy.happyhouse.model.UserDto;

@Mapper
public interface LikeMapper {
	public int insertLike(LikeDto like);
	public List<AptDto> listLike(String userid);
	public int deleteLike(int no);
}
