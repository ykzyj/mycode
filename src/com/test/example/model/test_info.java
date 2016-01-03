package com.sunnyit.example.model;

public class test_info {
	private int imageid;
	private String infotitle;
	private String infocontent;
	private String infotime;
	private Boolean ischeck;
	
	public Boolean getIscheck() {
		return ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	public String getInfotitle() {
		return infotitle;
	}
	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}
	public String getInfocontent() {
		return infocontent;
	}
	public void setInfocontent(String infocontent) {
		this.infocontent = infocontent;
	}
	public String getInfotime() {
		return infotime;
	}
	public void setInfotime(String infotime) {
		this.infotime = infotime;
	}
	
	public test_info(int imageid, String infotitle, String infotime, String infocontent,Boolean ischeck) {
		super();
		this.imageid = imageid;
		this.infotitle = infotitle;
		this.infocontent = infocontent;
		this.infotime = infotime;
		this.ischeck = ischeck;
	}
	public test_info() {
	}
}
