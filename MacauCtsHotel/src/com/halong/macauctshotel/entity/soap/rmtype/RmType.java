package com.halong.macauctshotel.entity.soap.rmtype;

import java.io.Serializable;
import java.util.List;

public class RmType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -869464717607444063L;
	private String hotelId;
	private String code;
	private String name;
	private String photo1;
	private String photo2;
	private String stdpax;
	private String stdrate;
	private String status;
	private String descript;
	private List<RateCode> rateCodeList;
	private String remarks;
	private String resume;

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public List<RateCode> getRateCodeList() {
		return rateCodeList;
	}

	public void setRateCodeList(List<RateCode> rateCodeList) {
		this.rateCodeList = rateCodeList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
