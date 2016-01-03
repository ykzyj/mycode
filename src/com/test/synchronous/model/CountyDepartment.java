package com.sunnyit.synchronous.model;

import com.sunnyit.common.annotation.NodeDes;
import com.sunnyit.common.annotation.NodeID;
import com.sunnyit.common.annotation.NodeName;
import com.sunnyit.common.annotation.NodePid;

/**   
* @Title: CountyDepartment.java 
* @Package com.sunnyit.synchronous.model 
* @Description: TODO
* @author yk
* @date 2015��9��15�� ����5:29:09 
* @version V1.0   
*/
public class CountyDepartment {
	@NodeID
	private String d_id;						//ID     
	@NodeName
	private String d_departmentname;			//��������
	@NodePid
	private String d_parentdepartmentid;		//������ID   
	private String d_parentdepartmentname;		//����������
	private String d_belongareaid;				//��������ID
	private String d_belongareaname;			//������������
	private String d_writerdepid;				//��д����ID
	@NodeDes
	private String d_level;						//����
	private String remark;						//��ע
	public String getD_id() {
		return d_id;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public String getD_departmentname() {
		return d_departmentname;
	}
	public void setD_departmentname(String d_departmentname) {
		this.d_departmentname = d_departmentname;
	}
	public String getD_parentdepartmentid() {
		return d_parentdepartmentid;
	}
	public void setD_parentdepartmentid(String d_parentdepartmentid) {
		this.d_parentdepartmentid = d_parentdepartmentid;
	}
	public String getD_parentdepartmentname() {
		return d_parentdepartmentname;
	}
	public void setD_parentdepartmentname(String d_parentdepartmentname) {
		this.d_parentdepartmentname = d_parentdepartmentname;
	}
	public String getD_belongareaid() {
		return d_belongareaid;
	}
	public void setD_belongareaid(String d_belongareaid) {
		this.d_belongareaid = d_belongareaid;
	}
	public String getD_belongareaname() {
		return d_belongareaname;
	}
	public void setD_belongareaname(String d_belongareaname) {
		this.d_belongareaname = d_belongareaname;
	}
	public String getD_writerdepid() {
		return d_writerdepid;
	}
	public void setD_writerdepid(String d_writerdepid) {
		this.d_writerdepid = d_writerdepid;
	}
	public String getD_level() {
		return d_level;
	}
	public void setD_level(String d_level) {
		this.d_level = d_level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}


