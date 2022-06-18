package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.AptDto;
import com.ssafy.happyhouse.model.LikeDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.LikeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/like-api")
//@CrossOrigin("*")
public class LikeController {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	@Autowired
	private LikeService likeService;
	
	@ApiOperation(value = "사용자가 즐겨찾기 한 아파트의 거래 리스트 반환.", response = List.class)
	@GetMapping("/like")
	public ResponseEntity<?> listLike(@RequestParam("userid") String userid){
		try {
			List<AptDto> list = likeService.listLike(userid);
			return new ResponseEntity<List<AptDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "관심지역 등록.")
	@PostMapping("/like")
	public ResponseEntity<?> insertLike(@RequestBody LikeDto like){	
		try {
			int ck = likeService.insertLike(like);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "관심지역 삭제.")
	@DeleteMapping("/like")
	public ResponseEntity<?> deleteLike(@RequestParam("no") int no){
		try {
			int ck = likeService.deleteLike(no);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
