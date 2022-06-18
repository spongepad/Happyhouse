package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.NoticeDto;

@Mapper
public interface QnAMapper {
	
	// QnA
	public List<NoticeDto> listQnA(int limit);
	public List<NoticeDto> searchQnA(@Param("type") String type,@Param("word") String word);
	public NoticeDto viewQnA(int no);
	public int insertQnA(NoticeDto dto);
	public int updateQnA(NoticeDto dto);
	public int deleteQnA(int no);
	
	// Answer
	public List<AnswerDto> listAnswer(int notice_no);
	public int insertAnswer(AnswerDto dto);
	public int updateAnswer(AnswerDto dto);
	public int deleteAnswer(int no);
	public int likeAnswer(int no);
	public int disLikeAnswer(int no);
	public int contactAnswer(int no);
	public int checkContact(int notice_no);
	public int checkLike(AnswerDto dto);
	public int insertLikeLog(AnswerDto dto);
	public int deleteLikeLog(AnswerDto dto);
}
