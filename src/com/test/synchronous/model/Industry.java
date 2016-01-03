package com.sunnyit.synchronous.model;
/**   
* @Title: Industry.java 
* @Package com.sunnyit.synchronous.model 
* @Description: TODO
* @author yk
* @date 2015年9月15日 下午3:24:23 
* @version V1.0   
*/
public class Industry {
	private String industryId;	//类型ID
	private String industryType;	//行业类型
	private String indexid;
	private String upperOrgId;  
	private String upperOrgName;
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getUpperOrgId() {
		return upperOrgId;
	}
	public void setUpperOrgId(String upperOrgId) {
		this.upperOrgId = upperOrgId;
	}
	public String getUpperOrgName() {
		return upperOrgName;
	}
	public void setUpperOrgName(String upperOrgName) {
		this.upperOrgName = upperOrgName;
	}
	
}


