package com.sunnyit.law.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.NodeDes;
import com.sunnyit.common.annotation.NodeID;
import com.sunnyit.common.annotation.NodeName;
import com.sunnyit.common.annotation.NodePid;
import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: ExcelCell.java 
* @Package com.sunnyit.law.model 
* @Description: TODO
* @author yk
* @date 2015年9月24日 上午9:48:02 
* @version V1.0   
*/
public class ExcelCell implements Serializable{
	@NodeID
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String c_id;
	@NodeDes
	private String c_title;
	private String c_row;
	@NodeName
	private String c_content;
	@NodePid
	private String c_parentId;
	private String c_rootId;
	private String c_index;
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getC_row() {
		return c_row;
	}
	public void setC_row(String c_row) {
		this.c_row = c_row;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_parentId() {
		return c_parentId;
	}
	public void setC_parentId(String c_parentId) {
		this.c_parentId = c_parentId;
	}
	public String getC_rootId() {
		return c_rootId;
	}
	public void setC_rootId(String c_rootId) {
		this.c_rootId = c_rootId;
	}
	public String getC_index() {
		return c_index;
	}
	public void setC_index(String c_index) {
		this.c_index = c_index;
	}
}


