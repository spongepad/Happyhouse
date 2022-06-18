package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.mapper.QnAMapper;


@Service
public class QnAServiceImpl implements QnAService{
	
	@Autowired
	private QnAMapper qnaMapper;

	@Override
	public List<NoticeDto> listQnA(int limit) {
		return qnaMapper.listQnA(limit);
	}
	
	@Override
	public List<NoticeDto> searchQnA(String type, String word) {
		return qnaMapper.searchQnA(type, word);
	}

	@Override
	public NoticeDto viewQnA(int no) {
		return qnaMapper.viewQnA(no);
	}

	@Override
	@Transactional
	public int insertQnA(NoticeDto dto) {
		return qnaMapper.insertQnA(dto);
	}

	@Override
	@Transactional
	public int updateQnA(NoticeDto dto) {
		return qnaMapper.updateQnA(dto);
	}

	@Override
	@Transactional
	public int deleteQnA(int no) {
		return qnaMapper.deleteQnA(no);
	}

	@Override
	public List<AnswerDto> listAnswer(int notice_no) {
		return qnaMapper.listAnswer(notice_no);
	}

	@Override
	@Transactional
	public int insertAnswer(AnswerDto dto) {
		return qnaMapper.insertAnswer(dto);
	}

	@Override
	@Transactional
	public int updateAnswer(AnswerDto dto) {
		return qnaMapper.updateAnswer(dto);
	}

	@Override
	@Transactional
	public int deleteAnswer(int no) {
		return qnaMapper.deleteAnswer(no);
	}

	@Override
	@Transactional
	public int likeAnswer(int no) {
		return qnaMapper.likeAnswer(no);
	}

	@Override
	@Transactional
	public int contactAnswer(int no) {
		return qnaMapper.contactAnswer(no);
	}

	@Override
	public int checkContact(int notice_no) {
		return qnaMapper.checkContact(notice_no);
	}

	@Override
	public int checkLike(AnswerDto dto) {
		return qnaMapper.checkLike(dto);
	}

	@Override
	@Transactional
	public int insertLikeLog(AnswerDto dto) {
		return qnaMapper.insertLikeLog(dto);
	}

	@Override
	@Transactional
	public int disLikeAnswer(int no) {
		return qnaMapper.disLikeAnswer(no);
	}

	@Override
	@Transactional
	public int deleteLikeLog(AnswerDto dto) {
		return qnaMapper.deleteLikeLog(dto);
	}

	

}
