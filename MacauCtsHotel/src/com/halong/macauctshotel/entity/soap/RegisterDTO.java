package com.halong.macauctshotel.entity.soap;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.halong.macauctshotel.wcf.AsyncAPIClient;

public class RegisterDTO {

	private String code;
	private String password;
	private String nickname;
	private String email;
	private String mobile;
	private String gender;
	private long custid;
	private String memberstatus;

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return code;
		case 1:
			return password;
		case 2:
			return nickname;
		case 3:
			return email;
		case 4:
			return mobile;
		case 5:
			return gender;
		case 6:
			return custid;
		case 7:
			return memberstatus;
		default:
			break;
		}
		return null;
	}

	public String getMemberstatus() {
		return memberstatus;
	}

	public void setMemberstatus(String memberstatus) {
		this.memberstatus = memberstatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

}
