package com.sunnyit.hiddencheck.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: SelfStandCheck.java 
* @Package com.sunnyit.hiddencheck.model 
* @Description: TODO
* @author yk
* @date 2015年9月8日 下午2:17:17 
* @version V1.0   
*/
public class SelfStandCheck implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String sc_uuid;
	private String sc_idd;
	private String sc_companyId;
	private String sc_companyName;
	private String sc_checkTime;
	private String sc_checkDept;
	private String sc_deadline;
	private String sc_state;
	private String sc_circle;
	private String sc_checkPerson;
	private String sc_completeTablePeople;
	private String sc_completeTableTime;
	private String sc_superviseOpinion;
	private String sc_superviseDept;
	private String sc_superviseTime;
	private String remark;
	public String getSc_uuid() {
		return sc_uuid;
	}
	public void setSc_uuid(String sc_uuid) {
		this.sc_uuid = sc_uuid;
	}
	public String getSc_idd() {
		return sc_idd;
	}
	public void setSc_idd(String sc_idd) {
		this.sc_idd = sc_idd;
	}
	public String getSc_companyId() {
		return sc_companyId;
	}
	public void setSc_companyId(String sc_companyId) {
		this.sc_companyId = sc_companyId;
	}
	public String getSc_companyName() {
		return sc_companyName;
	}
	public void setSc_companyName(String sc_companyName) {
		this.sc_companyName = sc_companyName;
	}
	public String getSc_checkTime() {
		return sc_checkTime;
	}
	public void setSc_checkTime(String sc_checkTime) {
		this.sc_checkTime = sc_checkTime;
	}
	public String getSc_checkDept() {
		return sc_checkDept;
	}
	public void setSc_checkDept(String sc_checkDept) {
		this.sc_checkDept = sc_checkDept;
	}
	public String getSc_deadline() {
		return sc_deadline;
	}
	public void setSc_deadline(String sc_deadline) {
		this.sc_deadline = sc_deadline;
	}
	public String getSc_state() {
		return sc_state;
	}
	public void setSc_state(String sc_state) {
		this.sc_state = sc_state;
	}
	public String getSc_circle() {
		return sc_circle;
	}
	public void setSc_circle(String sc_circle) {
		this.sc_circle = sc_circle;
	}
	public String getSc_checkPerson() {
		return sc_checkPerson;
	}
	public void setSc_checkPerson(String sc_checkPerson) {
		this.sc_checkPerson = sc_checkPerson;
	}
	public String getSc_completeTablePeople() {
		return sc_completeTablePeople;
	}
	public void setSc_completeTablePeople(String sc_completeTablePeople) {
		this.sc_completeTablePeople = sc_completeTablePeople;
	}
	public String getSc_completeTableTime() {
		return sc_completeTableTime;
	}
	public void setSc_completeTableTime(String sc_completeTableTime) {
		this.sc_completeTableTime = sc_completeTableTime;
	}
	public String getSc_superviseOpinion() {
		return sc_superviseOpinion;
	}
	public void setSc_superviseOpinion(String sc_superviseOpinion) {
		this.sc_superviseOpinion = sc_superviseOpinion;
	}
	public String getSc_superviseDept() {
		return sc_superviseDept;
	}
	public void setSc_superviseDept(String sc_superviseDept) {
		this.sc_superviseDept = sc_superviseDept;
	}
	public String getSc_superviseTime() {
		return sc_superviseTime;
	}
	public void setSc_superviseTime(String sc_superviseTime) {
		this.sc_superviseTime = sc_superviseTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}


