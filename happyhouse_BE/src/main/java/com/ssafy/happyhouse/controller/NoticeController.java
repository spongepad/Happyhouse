package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.ReplyDto;
import com.ssafy.happyhouse.model.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/notice-api")
//@CrossOrigin("*")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@ApiOperation(value = "게시판 글 작성.")
	@PostMapping("/notice")
	public ResponseEntity<?> insertNotice(@RequestBody NoticeDto dto) {
		try {
			int ck = noticeService.insertNotice(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "모든 게시판 글의 정보를 반환한다.", response = List.class)
	@GetMapping("/notice")
	public ResponseEntity<?> listNotice(@RequestParam(required = false, name = "limit") int limit) {
		try {
			List<NoticeDto> list = noticeService.searchAll(limit);
			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "검색된 게시판 글의 정보를 반환한다.", response = List.class)
	@GetMapping("/notice/search")
	public ResponseEntity<?> searchNotice(@RequestParam("type") String type, @RequestParam("word") String word) {
		try {
			List<NoticeDto> list = noticeService.searchNotice(type, word);
			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 페이징은 vue에서
//	@GetMapping("/notice")
//	public ResponseEntity<?> listNotice(@RequestParam Map<String, String> map) {
//		try {
//			List<NoticeDto> list = noticeService.listNotice(map);
//			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
	@GetMapping("/notice_KMP")
	public ResponseEntity<?> listNotice_KMP(@RequestParam Map<String, String> map) {
		try {
			List<NoticeDto> list = noticeService.listNotice_KMP(map);
			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시글의 상세정보 반환.", response = NoticeDto.class)
	@GetMapping("/view/{no}")
	public ResponseEntity<?> viewNotice(@PathVariable("no") int no) {
		try {
			NoticeDto dto = noticeService.viewNotice(no);
			List<ReplyDto> list = noticeService.listReply(no);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("notice", dto);
			map.put("replys", list);

			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시글 수정.")
	@PutMapping("/notice")
	public ResponseEntity<?> updateNotice(@RequestBody NoticeDto dto) {
		try {
			int ck = noticeService.updateNotice(dto);
			return new ResponseEntity<Integer> (ck, HttpStatus.OK);
			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@ApiOperation(value = "게시글 삭제.")
	@DeleteMapping("/notice/{no}")
	public ResponseEntity<?> deleteNotice(@PathVariable int no) {
		try {
			NoticeDto dto = new NoticeDto();
			dto.setNo(no);
			int ck = noticeService.deleteNotice(dto);
			return new ResponseEntity<Integer> (ck, HttpStatus.OK);
			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "댓글 등록.")
	@PostMapping("/reply")
	public ResponseEntity<?> insertReply(@RequestBody ReplyDto dto){
		try {
			logger.debug("reply insert: {}", dto.getNo());
			int ck = noticeService.insertReply(dto);
			return new ResponseEntity<Integer> (ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "댓글 수정.")
	@PutMapping("/reply")
	public ResponseEntity<?> updateReply(@RequestBody ReplyDto dto){
		try {
			logger.debug("reply update: {}", dto.getNo());
			int ck = noticeService.updateReply(dto);
			return new ResponseEntity<Integer> (ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "댓글 삭제.")
	@DeleteMapping("/reply")
	public ResponseEntity<?> deleteReply(@RequestParam("no") int no){
		try {
			logger.debug("reply delete: {}", no);
			int ck = noticeService.deleteReply(no);
			return new ResponseEntity<Integer> (ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
