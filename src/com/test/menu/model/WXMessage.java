package com.sunnyit.menu.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

public class WXMessage implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.YES)
	private String id;
	private int icon_id;
	private String title;
	private String msg;
	private String time;
	
	public WXMessage(){
		
	}
	public WXMessage(String title, String msg, String time) {
		this.title = title;
		this.msg = msg;
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(int icon_id) {
		this.icon_id = icon_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
