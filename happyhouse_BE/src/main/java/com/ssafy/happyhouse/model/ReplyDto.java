package com.ssafy.happyhouse.model;

public class ReplyDto {
	int no;
	int notice_no;
	String userid;
	String content;
	String regdate;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getNotice_no() {
		return notice_no;
	}
	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "ReplyDto [no=" + no + ", notice_no=" + notice_no + ", userid=" + userid + ", content=" + content
				+ ", regdate=" + regdate + "]";
	}
	
	
}
