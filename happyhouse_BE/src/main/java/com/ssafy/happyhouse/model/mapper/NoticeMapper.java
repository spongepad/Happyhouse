package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.ListParameterDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.ReplyDto;


@Mapper
public interface NoticeMapper {
	public List<NoticeDto> searchAll(int limit);
	public List<NoticeDto> searchNotice(@Param("type") String type,@Param("word") String word);
	public List<NoticeDto> listNotice(ListParameterDto listParameterDto);
	int getTotalCount(ListParameterDto listParameterDto);
	
	public NoticeDto viewNotice(int no);
	public int insertNotice(NoticeDto dto);
	public int deleteNotice(NoticeDto dto);
	public int updateNotice(NoticeDto dto);
	
	// 댓글
	public int insertReply(ReplyDto dto);
	public int updateReply(ReplyDto dto);
	public int deleteReply(int no);
	public List<ReplyDto> listReply(int notice_no);
}
