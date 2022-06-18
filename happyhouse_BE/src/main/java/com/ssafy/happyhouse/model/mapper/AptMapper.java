package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;

@Mapper
public interface AptMapper {
	
	List<SidoGugunDongCodeDto> getSido();
	List<SidoGugunDongCodeDto> getGugunInSido(String sido);
	List<SidoGugunDongCodeDto> getDongInGugun(String gugun);
	
	public void registApt(AptDto apt);

	public List<AptDto> searchApt(@Param("aptname") String name , @Param("dong") String dong);
}
