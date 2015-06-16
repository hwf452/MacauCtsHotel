package com.halong.macauctshotel.entity.soap;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import android.util.Log;

import com.halong.macauctshotel.wcf.AsyncAPIClient;

public class Gres implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3588384298920890483L;

	private long adults;
	private String arrDate;
	private String booker;
	private String bookTel;
	private String cardNo;
	private long custId;
	private String depDate;
	private String gstName;
	private long hotelId;
	private String msgType;
	private long nights;
	private String rateCode;
	private long rmQty;
	private String rmRate;
	private String rmType;
	private String remarks;
	private String resDate;
	private String restype;
	private String resClerk;
	private String bookerEmail;
	private String payMthd;

	/**
	 * 信用卡担保
	 */
	private String creditCard;
	private String creditDate;
	private String creditType;
	private String cvvCode;

	/**
	 * 不用带上访问
	 */
	private long resId;
	private String status;
	private String currencyname;
	private long accId;

	/**
	 * 自增
	 */
	private String hotelName;
	private String hotelAddres;
	private String hoteletel;
	private String price;
	private String rmTypeName;
	private String statusName;

	/**
	 * 评论
	 */
	private int level;
	private String comment;

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return adults;
		case 1:
			return arrDate;
		case 2:
			return booker;
		case 3:
			return bookTel;
		case 4:
			return cardNo;
		case 5:
			return custId;
		case 6:
			return depDate;
		case 7:
			return gstName;
		case 8:
			return hotelId;
		case 9:
			return msgType;
		case 10:
			return nights;
		case 11:
			return rateCode;
		case 12:
			return rmQty;
		case 13:
			return rmRate;
		case 14:
			return rmType;
		case 15:
			return remarks;
		case 16:
			return resDate;
		case 17:
			return creditCard;
		case 18:
			return creditDate;
		case 19:
			return creditType;
		case 20:
			return cvvCode;
		case 21:
			return restype;
		case 22:
			return resClerk;
		case 23:
			return bookerEmail;
		case 24:
			return payMthd;
		default:
			break;
		}
		return null;
	}

	public String getPayMthd() {
		return payMthd;
	}

	public void setPayMthd(String payMthd) {
		this.payMthd = payMthd;
	}

	public String getResClerk() {
		return resClerk;
	}

	public void setResClerk(String resClerk) {
		this.resClerk = resClerk;
	}

	public String getBookerEmail() {
		return bookerEmail;
	}

	public void setBookerEmail(String bookerEmail) {
		this.bookerEmail = bookerEmail;
	}

	public String getRestype() {
		return restype;
	}

	public void setRestype(String restype) {
		this.restype = restype;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(String creditDate) {
		this.creditDate = creditDate;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getCvvCode() {
		return cvvCode;
	}

	public void setCvvCode(String cvvCode) {
		this.cvvCode = cvvCode;
	}

	public long getAdults() {
		return adults;
	}

	public void setAdults(long adults) {
		this.adults = adults;
	}

	public String getArrDate() {
		return arrDate;
	}

	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}

	public String getBooker() {
		return booker;
	}

	public void setBooker(String booker) {
		this.booker = booker;
	}

	public String getBookTel() {
		return bookTel;
	}

	public void setBookTel(String bookTel) {
		this.bookTel = bookTel;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getGstName() {
		return gstName;
	}

	public void setGstName(String gstName) {
		this.gstName = gstName;
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public long getNights() {
		return nights;
	}

	public void setNights(long nights) {
		this.nights = nights;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public long getRmQty() {
		return rmQty;
	}

	public void setRmQty(long rmQty) {
		this.rmQty = rmQty;
	}

	public String getRmRate() {
		return rmRate;
	}

	public void setRmRate(String rmRate) {
		this.rmRate = rmRate;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelAddres() {
		return hotelAddres;
	}

	public void setHotelAddres(String hotelAddres) {
		this.hotelAddres = hotelAddres;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRmTypeName() {
		return rmTypeName;
	}

	public void setRmTypeName(String rmTypeName) {
		this.rmTypeName = rmTypeName;
	}

	public long getResId() {
		return resId;
	}

	public void setResId(long resId) {
		this.resId = resId;
	}

	public String getResDate() {
		return resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHoteletel() {
		return hoteletel;
	}

	public void setHoteletel(String hoteletel) {
		this.hoteletel = hoteletel;
	}

	public String getCurrencyname() {
		return currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

}
