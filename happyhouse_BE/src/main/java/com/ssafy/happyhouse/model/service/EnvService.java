package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.EnvDto;

public interface EnvService {
	List<EnvDto> find(Map<String, String> map) throws Exception;
}
