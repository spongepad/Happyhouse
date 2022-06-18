package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;
import com.ssafy.happyhouse.model.mapper.AptMapper;

@Service
public class AptServiceImpl implements AptService{
	
	@Autowired
	private AptMapper aptMapper;

	@Override
	public List<AptDto> searchApt(String name, String dong) {
		dong = (dong == null || dong.equals("동")) ? "" : dong;
		name = (name == null) ? "" : name;
		return aptMapper.searchApt(name, dong);
	}

	@Override
	public List<SidoGugunDongCodeDto> getSido() {
		return aptMapper.getSido();
	}

	@Override
	public List<SidoGugunDongCodeDto> getGugunInSido(String sido) {
		return aptMapper.getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunDongCodeDto> getDongInGugun(String gugun) {
		return aptMapper.getDongInGugun(gugun);
	}

}
