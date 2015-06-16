package com.halong.macauctshotel.entity.soap;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.halong.macauctshotel.wcf.AsyncAPIClient;

public class OrderDTO   {

	private long custId;

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return custId;
		default:
			break;
		}
		return null;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	
}
