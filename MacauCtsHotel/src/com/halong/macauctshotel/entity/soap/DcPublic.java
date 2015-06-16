package com.halong.macauctshotel.entity.soap;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DcPublic implements KvmSerializable {

	private String category;
	private String code;
	private String cname;
	private String ename;
	private String remarks;
	private String status;
	private String seqid;
	
	//自加
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		Object str = "";
		switch (arg0) {
		case 0:
			str = category;
			break;
		case 1:
			str = code;
			break;
		case 2:
			str = cname;
			break;
		case 3:
			str = ename;
			break;
		case 4:
			str = remarks;
			break;
		case 5:
			str = status;
			break;
		case 6:
			str = seqid;
			break;
		default:
			break;
		}
		return str;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "category";
			break;
		case 1:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "code";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "cname";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "ename";
			break;
		case 4:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "remarks";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "status";
			break;
		case 6:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "seqid";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			return;
		}
		switch (arg0) {
		case 0:
			this.category = arg1.toString();
			break;
		case 1:
			this.code = arg1.toString();
			break;
		case 2:
			this.cname = arg1.toString();
			break;
		case 3:
			this.ename = arg1.toString();
			break;
		case 4:
			this.remarks = arg1.toString();
			break;
		case 5:
			this.status = arg1.toString();
			break;
		case 6:
			this.seqid = arg1.toString();
			break;
		default:
			break;
		}
	}

}
