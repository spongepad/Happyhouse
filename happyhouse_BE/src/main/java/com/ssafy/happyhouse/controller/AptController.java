package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;
import com.ssafy.happyhouse.model.service.AptService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/apt-api")
//@CrossOrigin("*")
public class AptController {
	
	private static final Logger logger = LoggerFactory.getLogger(AptController.class);
	
	@Autowired
	private AptService aptService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunDongCodeDto>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(aptService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunDongCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(aptService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<SidoGugunDongCodeDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(aptService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 거래정보 반환. (아파트 이름, 동 코드 입력)", response = List.class)
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestParam(name = "aptname", defaultValue = "") String aptname,
			@RequestParam(name = "dong", defaultValue = "") String dong) {
		
		try {
			List<AptDto> list = aptService.searchApt(aptname, dong);
			return new ResponseEntity<List<AptDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
