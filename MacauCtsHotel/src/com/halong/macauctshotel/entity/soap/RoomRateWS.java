package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;
import java.util.List;

public class RoomRateWS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119816304225365653L;
	
	private RateCode rateCodes;
	private RmType rmTypes;
	private String rmStatus;
	private String dayRate;
	private String totalRate;
	
	
	

	public String getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}
	public String getDayRate() {
		return dayRate;
	}
	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}
	public RateCode getRateCodes() {
		return rateCodes;
	}
	public void setRateCodes(RateCode rateCodes) {
		this.rateCodes = rateCodes;
	}
	public RmType getRmTypes() {
		return rmTypes;
	}
	public void setRmTypes(RmType rmTypes) {
		this.rmTypes = rmTypes;
	}
	public String getRmStatus() {
		return rmStatus;
	}
	public void setRmStatus(String rmStatus) {
		this.rmStatus = rmStatus;
	}
	
	
}
