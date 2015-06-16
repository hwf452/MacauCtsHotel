package com.halong.macauctshotel.entity.async;

import java.io.Serializable;

public class Travel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8172626805670839615L;
	private int id;
	private String title;
	private String add_time;
	private String info;
	private String img;
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
