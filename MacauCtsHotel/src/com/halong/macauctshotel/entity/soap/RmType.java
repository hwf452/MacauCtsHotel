package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;

public class RmType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1713085445109687694L;

	private String name;
	private String photo1;
	private String photo2;
	private String stdpax;
	private String stdrate;
	private String status;
	private String code;
	private String features;
	
	
	
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getStdpax() {
		return stdpax;
	}
	public void setStdpax(String stdpax) {
		this.stdpax = stdpax;
	}
	public String getStdrate() {
		return stdrate;
	}
	public void setStdrate(String stdrate) {
		this.stdrate = stdrate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
