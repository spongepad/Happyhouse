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

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.service.QnAService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/qna-api")
//@CrossOrigin("*")
public class QnAController {
	
	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	
	@Autowired
	private QnAService qnaService;
	
	@ApiOperation(value = "질문글 리스트 반환.", response = List.class)
	@GetMapping("/qna")
	public ResponseEntity<?> listQnA(@RequestParam(required = false, name = "limit") int limit){
		try {
			logger.debug("QnA list: {}");
			
			List<NoticeDto> list = qnaService.listQnA(limit);
			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "검색된 질문글의 정보를 반환한다.", response = List.class)
	@GetMapping("/qna/search")
	public ResponseEntity<?> searchQnA(@RequestParam("type") String type, @RequestParam("word") String word) {
		try {
			List<NoticeDto> list = qnaService.searchQnA(type, word);
			return new ResponseEntity<List<NoticeDto>> (list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "질문글 등록.")
	@PostMapping("/qna")
	public ResponseEntity<?> insertQnA(@RequestBody NoticeDto dto){
		try {
			logger.debug("QnA insert: {}", dto.getUserid());
			
			int ck = qnaService.insertQnA(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "질문글 수정.")
	@PutMapping("/qna")
	public ResponseEntity<?> updateQnA(@RequestBody NoticeDto dto){
		try {
			logger.debug("QnA update: {}", dto.getUserid());
			
			int ck = qnaService.updateQnA(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "질문글 삭제.")
	@DeleteMapping("/qna")
	public ResponseEntity<?> deleteQnA(@RequestParam("no") int no){
		try {
			logger.debug("QnA delete: {}", no);
			
			int ck = qnaService.deleteQnA(no);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "질문글 상세정보 반환.", response = Map.class)
	@GetMapping("/qna/{no}")
	public ResponseEntity<?> viewQnA(@PathVariable("no") int no){
		try {
			logger.debug("QnA view: {}", no);
			
			NoticeDto qna = qnaService.viewQnA(no);
			List<AnswerDto> answers = qnaService.listAnswer(no);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("qna", qna);
			map.put("answers", answers);
			
			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 등록.")
	@PostMapping("/answer")
	public ResponseEntity<?> insertAnswer(@RequestBody AnswerDto dto) {
		try {
			logger.debug("QnA answer insert: {}", dto.getNotice_no());
			
			int ck = qnaService.insertAnswer(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 수정.")
	@PutMapping("/answer")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerDto dto) {
		try {
			logger.debug("QnA answer update: {}", dto.getNotice_no());
			
			int ck = qnaService.updateAnswer(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 삭제.")
	@DeleteMapping("/answer")
	public ResponseEntity<?> deleteAnswer(@RequestParam("no") int no) {
		try {
			logger.debug("QnA answer delete: {}", no);
			
			int ck = qnaService.deleteAnswer(no);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 추전.")
	@GetMapping("/answer/like")
	public ResponseEntity<?> likeAnswer(@RequestParam("no") int no, @RequestParam("userid") String userid){
		try {
			logger.debug("QnA answer like: {}", no);
			AnswerDto dto = new AnswerDto();
			dto.setNo(no);
			dto.setUserid(userid);
			
			int ck = qnaService.likeAnswer(no) +
					qnaService.insertLikeLog(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 추전 취소.")
	@GetMapping("/answer/dislike")
	public ResponseEntity<?> disLikeAnswer(@RequestParam("no") int no, @RequestParam("userid") String userid){
		try {
			logger.debug("QnA answer dislike: {}", no);
			AnswerDto dto = new AnswerDto();
			dto.setNo(no);
			dto.setUserid(userid);
			
			int ck = qnaService.disLikeAnswer(no) +
					qnaService.deleteLikeLog(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "해당 질문글에 사용자가 추천을 했는지 확인.")
	@GetMapping("/answer/like/check")
	public ResponseEntity<?> checkLike(@RequestParam("answer_no") int answer_no, @RequestParam("userid") String userid){
		try {
			logger.debug("QnA answer likeCheck: {}", answer_no);
			AnswerDto dto = new AnswerDto();
			dto.setNo(answer_no);
			dto.setUserid(userid);
			
			int ck = qnaService.checkLike(dto);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "답변 채택.")
	@GetMapping("/answer/contact/{no}")
	public ResponseEntity<?> contactAnswer(@PathVariable("no") int no){
		try {
			logger.debug("QnA answer contact: {}", no);
			
			int ck = qnaService.contactAnswer(no);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "해당 질문글에 채택된 답변 개수 확인.")
	@GetMapping("/answer/contact/check/{notice_no}")
	public ResponseEntity<?> checkContact(@PathVariable("notice_no") int notice_no){
		try {
			logger.debug("QnA answer contactCheck: {}", notice_no);
			
			int ck = qnaService.checkContact(notice_no);
			return new ResponseEntity<Integer>(ck, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
