package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;

import android.R.integer;

public class Sale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9039676236700185166L;

	private int id;
	private String photo;
	private String detail;
	private String date;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
