package com.halong.macauctshotel.entity.soap;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.halong.macauctshotel.wcf.AsyncAPIClient;

public class GetPaymentDTO {

	private long hotelId;
	private String ratecode;
	private String rmtype;
	private String arrDate;
	private String depDate;

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return hotelId;
		case 1:
			return ratecode;
		case 2:
			return rmtype;
		case 3:
			return arrDate;
		case 4:
			return depDate;
		default:
			break;
		}
		return null;
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getRatecode() {
		return ratecode;
	}

	public void setRatecode(String ratecode) {
		this.ratecode = ratecode;
	}

	public String getRmtype() {
		return rmtype;
	}

	public void setRmtype(String rmtype) {
		this.rmtype = rmtype;
	}

	public String getArrDate() {
		return arrDate;
	}

	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

}
