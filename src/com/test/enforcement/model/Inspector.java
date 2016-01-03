package com.sunnyit.enforcement.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Inspector.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015��9��26�� ����11:23:07 
* @version V1.0   
*/
public class Inspector implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	 private String checkid; 			      	//�����Ա���
	 private String ck_id;  			    	//ר������
	 private String inspectorname; 			//��Ա����
	 private String inspectorduty;			 	//��Աְ��/ְ��
	 private String inspectortype;			 	//��Ա����
	 private String remark; 					//��ע
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public String getInspectorname() {
		return inspectorname;
	}
	public void setInspectorname(String inspectorname) {
		this.inspectorname = inspectorname;
	}
	public String getInspectorduty() {
		return inspectorduty;
	}
	public void setInspectorduty(String inspectorduty) {
		this.inspectorduty = inspectorduty;
	}
	public String getInspectortype() {
		return inspectortype;
	}
	public void setInspectortype(String inspectortype) {
		this.inspectortype = inspectortype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}


