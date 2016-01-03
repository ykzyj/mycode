package com.sunnyit.synchronous.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: UpInfo.java 
* @Package com.sunnyit.synchronous.model 
* @Description: TODO
* @author yk
* @date 2015年9月15日 下午4:46:59 
* @version V1.0   
*/
public class UpInfo {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String U_Id; 
	private String InfoTable; 
	private String InfoId;
	private String OperateType;
	private String remark;
	public String getU_Id() {
		return U_Id;
	}
	public void setU_Id(String u_Id) {
		U_Id = u_Id;
	}
	public String getInfoTable() {
		return InfoTable;
	}
	public void setInfoTable(String infoTable) {
		InfoTable = infoTable;
	}
	public String getInfoId() {
		return InfoId;
	}
	public void setInfoId(String infoId) {
		InfoId = infoId;
	}
	public String getOperateType() {
		return OperateType;
	}
	public void setOperateType(String operateType) {
		OperateType = operateType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
}


