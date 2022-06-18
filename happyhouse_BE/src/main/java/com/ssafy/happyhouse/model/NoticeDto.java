package com.ssafy.happyhouse.model;

public class NoticeDto {
	int no;
	int ans_cnt;
	String title;
	String contents;
	String userid;
	String regdate;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	public int getAns_cnt() {
		return ans_cnt;
	}
	public void setAns_cnt(int ans_cnt) {
		this.ans_cnt = ans_cnt;
	}
	@Override
	public String toString() {
		return "NoticeDto [no=" + no + ", title=" + title + ", contents=" + contents + ", userid=" + userid
				+ ", regdate=" + regdate + "]";
	}
	
}
