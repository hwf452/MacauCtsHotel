package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;

public class RateCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -870028772035288782L;
	
	private String name;
	private String currency;
	private String rateCode;
	
	
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
	
	
}
