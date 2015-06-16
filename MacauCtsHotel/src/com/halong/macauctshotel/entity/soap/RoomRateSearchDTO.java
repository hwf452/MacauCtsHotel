package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.halong.macauctshotel.wcf.AsyncAPIClient;

public class RoomRateSearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992843077551281227L;

	private String hotelId;
	private String personNum;
	private String roomNum;
	private String apprDate;
	private String depDate;

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return hotelId;
		case 1:
			return personNum;
		case 2:
			return roomNum;
		case 3:
			return apprDate;
		case 4:
			return depDate;
		default:
			break;
		}
		return null;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getApprDate() {
		return apprDate;
	}

	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

}