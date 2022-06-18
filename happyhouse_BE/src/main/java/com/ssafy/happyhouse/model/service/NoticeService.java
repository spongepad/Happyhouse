package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.ReplyDto;
import com.ssafy.happyhouse.util.PageNavigation;

public interface NoticeService {
	// 게시글
	public List<NoticeDto> searchAll(int limit);
	public List<NoticeDto> searchNotice(String type, String word);
	public List<NoticeDto> listNotice(Map<String, String> map);
	public PageNavigation makePageNavigation(Map<String, String> map);
	public NoticeDto viewNotice(int no);
	public int insertNotice(NoticeDto dto);
	public int deleteNotice(NoticeDto dto);
	public int updateNotice(NoticeDto dto);
	public List<NoticeDto> listNotice_KMP(Map<String, String> map);
	
	// 댓글
	public int insertReply(ReplyDto dto);
	public int updateReply(ReplyDto dto);
	public int deleteReply(int no);
	public List<ReplyDto> listReply(int notice_no);
}
