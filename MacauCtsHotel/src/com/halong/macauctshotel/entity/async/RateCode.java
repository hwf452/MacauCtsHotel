package com.halong.macauctshotel.entity.async;

public class RateCode {
	private String rateCode;
	private String name;
	private String currency;
	private String rateCat;
	private String remarks   ;
	private String descript ;
	private String status;
	private String totalRate ;
	private String rmStatus;
	private String dayRate;
	private float[] rateDate;
	
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRateCat() {
		return rateCat;
	}
	public void setRateCat(String rateCat) {
		this.rateCat = rateCat;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}
	public String getRmStatus() {
		return rmStatus;
	}
	public void setRmStatus(String rmStatus) {
		this.rmStatus = rmStatus;
	}
	public String getDayRate() {
		return dayRate;
	}
	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}
	public float[] getRateDate() {
		return rateDate;
	}
	public void setRateDate(float[] rateDate) {
		this.rateDate = rateDate;
	}
	
	
}
