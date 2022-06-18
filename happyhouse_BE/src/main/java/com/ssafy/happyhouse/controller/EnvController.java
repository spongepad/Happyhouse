package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.EnvDto;
import com.ssafy.happyhouse.model.service.EnvService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/env-api")
//@CrossOrigin("*")
public class EnvController {
	
	private static final Logger logger = LoggerFactory.getLogger(EnvController.class);
	
	@Autowired
	private EnvService envService;
	
	@ApiOperation(value = "주변 환경처리 업소 반환.", response = List.class)
	@GetMapping("/find")
	public ResponseEntity<?> findEnv(@RequestParam Map<String, String> map){
		try {
			List<EnvDto> list = envService.find(map);
			
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<EnvDto>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
