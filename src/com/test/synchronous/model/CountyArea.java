package com.sunnyit.synchronous.model;

import com.sunnyit.common.annotation.NodeDes;
import com.sunnyit.common.annotation.NodeID;
import com.sunnyit.common.annotation.NodeName;
import com.sunnyit.common.annotation.NodePid;

/**   
* @Title: CountyArea.java 
* @Package com.sunnyit.synchronous.model 
* @Description: TODO
* @author yk
* @date 2015年9月15日 下午5:28:20 
* @version V1.0   
*/
public class CountyArea {
	@NodeID
	private String a_id;      				//ID  
	@NodeName
	private String a_areaname;       		//区域名称
	@NodePid
	private String a_parentareaid;   		//父区域ID
	private String a_parentareaname; 		//父区域名称
	private String a_writerdepid;    		//填写部门ID
	@NodeDes
	private String a_level;          		//级别		0:西安, 1:区县, 2:乡镇街办,
	private String remark;           		//备注
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public String getA_areaname() {
		return a_areaname;
	}
	public void setA_areaname(String a_areaname) {
		this.a_areaname = a_areaname;
	}
	public String getA_parentareaid() {
		return a_parentareaid;
	}
	public void setA_parentareaid(String a_parentareaid) {
		this.a_parentareaid = a_parentareaid;
	}
	public String getA_parentareaname() {
		return a_parentareaname;
	}
	public void setA_parentareaname(String a_parentareaname) {
		this.a_parentareaname = a_parentareaname;
	}
	public String getA_writerdepid() {
		return a_writerdepid;
	}
	public void setA_writerdepid(String a_writerdepid) {
		this.a_writerdepid = a_writerdepid;
	}
	public String getA_level() {
		return a_level;
	}
	public void setA_level(String a_level) {
		this.a_level = a_level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}


