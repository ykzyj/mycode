package com.sunnyit.law.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: HiddenStandardFile.java 
* @Package com.sunnyit.law.model 
* @Description: TODO
* @author yk
* @date 2015年9月24日 上午9:48:33 
* @version V1.0   
*/
public class HiddenStandardFile implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String hf_id;
	private String hf_name;
	private String hf_userObj;		//0：企业用      1：行业部门
	private String hf_time;
	private String hf_type;
	public String getHf_id() {
		return hf_id;
	}
	public void setHf_id(String hf_id) {
		this.hf_id = hf_id;
	}
	public String getHf_name() {
		return hf_name;
	}
	public void setHf_name(String hf_name) {
		this.hf_name = hf_name;
	}
	public String getHf_userObj() {
		return hf_userObj;
	}
	public void setHf_userObj(String hf_userObj) {
		this.hf_userObj = hf_userObj;
	}
	public String getHf_time() {
		return hf_time;
	}
	public void setHf_time(String hf_time) {
		this.hf_time = hf_time;
	}
	public String getHf_type() {
		return hf_type;
	}
	public void setHf_type(String hf_type) {
		this.hf_type = hf_type;
	}
}


