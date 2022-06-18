package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.LikeDto;
import com.ssafy.happyhouse.model.UserDto;

public interface LikeService {
	public int insertLike(LikeDto like);
	public List<AptDto> listLike(String userid);
	public int deleteLike(int no);
}
