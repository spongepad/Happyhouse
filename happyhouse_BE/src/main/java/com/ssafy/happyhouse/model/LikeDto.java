package com.ssafy.happyhouse.model;

public class LikeDto {
	private int no;
	private String userid;
	private String aptcode;
	
	public LikeDto() {
		super();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAptcode() {
		return aptcode;
	}

	public void setAptcode(String aptcode) {
		this.aptcode = aptcode;
	}

	@Override
	public String toString() {
		return "LikeDto [no=" + no + ", userid=" + userid + ", aptcode=" + aptcode + "]";
	}

	
}
