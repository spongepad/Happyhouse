package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.LikeDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private LikeMapper likeMapper;

	@Override
	public List<AptDto> listLike(String userid) {
		return likeMapper.listLike(userid);
	}

	@Override
	@Transactional
	public int insertLike(LikeDto like) {
		return likeMapper.insertLike(like);
	}

	@Override
	@Transactional
	public int deleteLike(int no) {
		return likeMapper.deleteLike(no);
	}

}
