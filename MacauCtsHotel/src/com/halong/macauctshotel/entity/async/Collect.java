package com.halong.macauctshotel.entity.async;

import java.io.Serializable;

public class Collect implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4112640129425327681L;
	private String hotelid;
	private String address;
	private float star;
	private String photo;
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
