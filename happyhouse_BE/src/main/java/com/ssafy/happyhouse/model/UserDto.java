package com.ssafy.happyhouse.model;

public class UserDto {
	private String userid;
	private String password;
	private String username;
	private String email;
	private String tel;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "UserDto [userid=" + userid + ", password=" + password + ", email=" + email + ", tel=" + tel
				+ ", username=" + username + "]";
	}
}
