package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.EnvDto;
import com.ssafy.happyhouse.model.mapper.EnvMapper;

@Service
public class EnvServiceImpl implements EnvService{
	
	@Autowired
	private EnvMapper envMapper;

	@Override
	public List<EnvDto> find(Map<String, String> map) throws Exception {
		if(map.get("type").equals("air")) {
			map.put("type", "대기배출업소관리");
		} else if(map.get("type").equals("water")) {
			map.put("type", "폐수배출업소관리");
		}
		return envMapper.find(map);
	}

}
