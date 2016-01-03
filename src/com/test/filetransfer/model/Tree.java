package com.sunnyit.filetransfer.model;
/**   
* @Title: Tree.java 
* @Package com.sunnyit.filetransfer.model 
* @Description: TODO
* @author yk
* @date 2015年12月3日 下午5:30:36 
* @version V1.0   
*/
public class Tree {
	private String id;
	private String pId;
	private String name;
	private boolean open;
	private String userId;
	private String col;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
}


