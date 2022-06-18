package com.ssafy.happyhouse.model.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.ListParameterDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.ReplyDto;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;
import com.ssafy.happyhouse.util.KMP_algo;
import com.ssafy.happyhouse.util.PageNavigation;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public List<NoticeDto> searchAll(int limit) {
		return noticeMapper.searchAll(limit);
	}

	@Override
	@Transactional
	public int insertNotice(NoticeDto dto) {
		return noticeMapper.insertNotice(dto);
	}

	@Override
	@Transactional
	public int deleteNotice(NoticeDto dto) {
		return noticeMapper.deleteNotice(dto);
	}

	@Override
	public NoticeDto viewNotice(int no) {
		return noticeMapper.viewNotice(no);
	}

	@Override
	@Transactional
	public int updateNotice(NoticeDto dto) {
		return noticeMapper.updateNotice(dto);
	}
	
	@Override
	public List<NoticeDto> searchNotice(String type, String word) {
		return noticeMapper.searchNotice(type, word);
	}

	@Override
	public List<NoticeDto> listNotice(Map<String, String> map) {
		int pgno = map.get("pg") != null ? Integer.parseInt(map.get("pg")) : 1;
		int countPerPage = 5;
		int start = (pgno - 1) * countPerPage;

		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setWord(map.get("word") == null ? "" : map.get("word").trim());
		listParameterDto.setStart(start);
		listParameterDto.setCountPerPage(countPerPage);
		return noticeMapper.listNotice(listParameterDto);
	}

	@Override
	public List<NoticeDto> listNotice_KMP(Map<String, String> map) {
		int pgno = map.get("pg") != null ? Integer.parseInt(map.get("pg")) : 1;
		int countPerPage = 5;
		int start = (pgno - 1) * countPerPage;

		List<NoticeDto> list;

		list = noticeMapper.searchAll(0);
		if (map.get("word") == null || map.get("word").equals("")) {
			if (list.size() > start + countPerPage)
				return list.subList(start, start + countPerPage);
			return list.subList(start, list.size());
		}
			
		List<NoticeDto> resultlist = new LinkedList<NoticeDto>();
		for (int i = 0; i < list.size(); i++) {
			NoticeDto tmp = list.get(i);
			// System.out.println("KMP: "+tmp.getTitle()+", "+map.get("word").trim()+"
			// result: "+KMP(tmp.getTitle(),map.get("word").trim()));
			int[] pi = KMP_algo.KMP_getPi(map.get("word").trim());
			if (KMP_algo.KMP(tmp.getTitle(), map.get("word").trim(), pi)) {
				resultlist.add(tmp);
			}
		}
		if (resultlist.size() > start + countPerPage)
			return resultlist.subList(start, start + countPerPage);
		return resultlist.subList(start, resultlist.size());
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = map.get("pg") != null ? Integer.parseInt(map.get("pg")) : 1;
		int navisize = 5;
		int countPerPage = 5;

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setCountPerPage(countPerPage);
		pageNavigation.setNaviSize(navisize);

		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setWord(map.get("word") == null ? "" : map.get("word").trim());

		int totalCount = noticeMapper.getTotalCount(listParameterDto);
		pageNavigation.setTotalCount(totalCount);

		int totalPageCount = (totalCount - 1) / countPerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		pageNavigation.setStartRange(currentPage <= navisize);

		boolean endRange = (totalPageCount - 1) / navisize * navisize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}
	
	@Override
	@Transactional
	public int insertReply(ReplyDto dto) {
		return noticeMapper.insertReply(dto);
	}

	@Override
	@Transactional
	public int updateReply(ReplyDto dto) {
		return noticeMapper.updateReply(dto);
	}

	@Override
	@Transactional
	public int deleteReply(int no) {
		return noticeMapper.deleteReply(no);
	}

	@Override
	public List<ReplyDto> listReply(int notice_no) {
		return noticeMapper.listReply(notice_no);
	}


}
