package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;

public interface AptService {
	List<SidoGugunDongCodeDto> getSido();
	List<SidoGugunDongCodeDto> getGugunInSido(String sido);
	List<SidoGugunDongCodeDto> getDongInGugun(String gugun);
	public List<AptDto> searchApt(String name, String dong);
}
