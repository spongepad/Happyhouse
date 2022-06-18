package com.ssafy.happyhouse.model;

public class AnswerDto {
	int no;
	int notice_no;
	int like;
	String userid;
	String content;
	String contact;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regadte) {
		this.regdate = regadte;
	}
	
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "QnADto [no=" + no + ", notice_no=" + notice_no + ", content=" + content + ", contact=" + contact
				+ ", regdate=" + regdate + "]";
	}
	
	
}
