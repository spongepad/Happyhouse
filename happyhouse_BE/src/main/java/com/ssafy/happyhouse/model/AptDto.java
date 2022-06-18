package com.ssafy.happyhouse.model;

public class AptDto {
	private String likeno;
	private String aptno;
	private String apartmentname;
	private String dongname;
	private String dongcode;
	private String jibun;
	private String aptcode;
	private String dealamount;
	private String area;
	private String floor;
	private String type;
	private String lat;
	private String lng;

	private int buildyear;
	private int dealyear;
	private int dealmonth;
	private int dealday;
	
	

	public String getLikeno() {
		return likeno;
	}

	public void setLikeno(String likeno) {
		this.likeno = likeno;
	}

	public String getApartmentname() {
		return apartmentname;
	}

	public void setApartmentname(String apartmentname) {
		this.apartmentname = apartmentname;
	}

	public String getDongname() {
		return dongname;
	}

	public void setDongname(String dongname) {
		this.dongname = dongname;
	}

	public String getAptname() {
		return apartmentname;
	}

	public void setAptname(String aptname) {
		this.apartmentname = aptname;
	}

	public String getDong() {
		return dongname;
	}

	public void setDong(String dong) {
		this.dongname = dong;
	}

	public int getBuildyear() {
		return buildyear;
	}

	public void setBuildyear(int buildyear) {
		this.buildyear = buildyear;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}

	public String getAptcode() {
		return aptcode;
	}

	public void setAptcode(String areacode) {
		this.aptcode = areacode;
	}

	public String getDealamount() {
		return dealamount;
	}

	public void setDealamount(String dealamount) {
		this.dealamount = dealamount;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDealyear() {
		return dealyear;
	}

	public void setDealyear(int dealyear) {
		this.dealyear = dealyear;
	}

	public int getDealmonth() {
		return dealmonth;
	}

	public void setDealmonth(int dealmonth) {
		this.dealmonth = dealmonth;
	}

	public int getDealday() {
		return dealday;
	}

	public void setDealday(int dealday) {
		this.dealday = dealday;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	

	public String getAptno() {
		return aptno;
	}

	public void setAptno(String aptno) {
		this.aptno = aptno;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}

	@Override
	public String toString() {
		return "AptDto [likeno=" + likeno + ", aptno=" + aptno + ", apartmentname=" + apartmentname + ", dongname="
				+ dongname + ", dongcode=" + dongcode + ", jibun=" + jibun + ", aptcode=" + aptcode + ", dealamount="
				+ dealamount + ", area=" + area + ", floor=" + floor + ", type=" + type + ", lat=" + lat + ", lng="
				+ lng + ", buildyear=" + buildyear + ", dealyear=" + dealyear + ", dealmonth=" + dealmonth
				+ ", dealday=" + dealday + "]";
	}
}
