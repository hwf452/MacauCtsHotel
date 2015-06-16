package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;

public class CustomerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2150288026670551028L;

	private long custid;
	private String code;
	private String gstname;
	private String engname;
	private String gender;
	private String mobile;
	private String email;
	private String cardno;
	private String nickname;
	private String pointaccid;
	private String memberstatus;
	
	
	

	public String getMemberstatus() {
		return memberstatus;
	}

	public void setMemberstatus(String memberstatus) {
		this.memberstatus = memberstatus;
	}

	public String getPointaccid() {
		return pointaccid;
	}

	public void setPointaccid(String pointaccid) {
		this.pointaccid = pointaccid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGstname() {
		return gstname;
	}

	public void setGstname(String gstname) {
		this.gstname = gstname;
	}

	public String getEngname() {
		return engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

}