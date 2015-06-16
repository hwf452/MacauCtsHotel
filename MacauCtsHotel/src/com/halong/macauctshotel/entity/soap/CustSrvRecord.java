package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;

public class CustSrvRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1755068320271759271L;

	private int level;
	private String ordernum;
	private String hotelId;
	private String  rmTypeName;
	private String addtime;
	private String comment;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getRmTypeName() {
		return rmTypeName;
	}
	public void setRmTypeName(String rmTypeName) {
		this.rmTypeName = rmTypeName;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	
}
