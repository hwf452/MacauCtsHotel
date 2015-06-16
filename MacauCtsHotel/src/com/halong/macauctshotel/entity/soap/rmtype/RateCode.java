package com.halong.macauctshotel.entity.soap.rmtype;

import java.io.Serializable;

public class RateCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2314666660950701333L;
	private String rateCode;
	private String name;
	private String currency;
	private String rateCat;
	private String remarks;
	private String descript;
	private String status;
	private String rmStatus;
	private String totalRate;
	private String dayRate;
	private String rateDate;
	
	private String lowest;
	private String currencyName;
	
	
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getLowest() {
		return lowest;
	}
	public void setLowest(String lowest) {
		this.lowest = lowest;
	}
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
	public String getRmStatus() {
		return rmStatus;
	}
	public void setRmStatus(String rmStatus) {
		this.rmStatus = rmStatus;
	}
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
	public String getRateDate() {
		return rateDate;
	}
	public void setRateDate(String rateDate) {
		this.rateDate = rateDate;
	}
}
