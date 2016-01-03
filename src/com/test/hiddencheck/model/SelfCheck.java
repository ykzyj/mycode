package com.sunnyit.hiddencheck.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: SelfCheck.java 
* @Package com.sunnyit.hiddencheck.model 
* @Description: TODO
* @author yk
* @date 2015年9月8日 下午2:08:59 
* @version V1.0   
*/
public class SelfCheck implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String	sc_uuId;
	private String	sc_id;
	private String	sc_companyId;
	private String	sc_checkTime;
	private String	sc_checkedDepartment;
	private String	sc_checkedJob;
	private String	sc_checkingDepartment;
	private String	sc_inspector;
	private String	sc_hiddenCondition;
	private String	sc_isHave;
	private String	sc_fixMeasure;
	private String	sc_startTime;
	private String	sc_deadline;
	private String	sc_state;
	private String	sc_completeTablePeople;
	private String	sc_completeTableTime;
	private String sc_companyName;
	private String	remark;
	private int sc_checkedTimes;
	private String sc_superviseOpinion;
	private String sc_superviseDept;
	private String sc_superviseTime;
	private String sco_fixresult;
	private String sco_acceptopinion;
	private String sco_acceptanceDepartment;
	private String sco_acceptanceTime;
	private String sco_accepters;
	private String sco_completeTablePeople;
	private String sco_completeTableTime;
	private String sco_isAccept;
	public String getSc_uuId() {
		return sc_uuId;
	}
	public void setSc_uuId(String sc_uuId) {
		this.sc_uuId = sc_uuId;
	}
	public String getSc_id() {
		return sc_id;
	}
	public void setSc_id(String sc_id) {
		this.sc_id = sc_id;
	}
	public String getSc_companyId() {
		return sc_companyId;
	}
	public void setSc_companyId(String sc_companyId) {
		this.sc_companyId = sc_companyId;
	}
	public String getSc_checkTime() {
		return sc_checkTime;
	}
	public void setSc_checkTime(String sc_checkTime) {
		this.sc_checkTime = sc_checkTime;
	}
	public String getSc_checkedDepartment() {
		return sc_checkedDepartment;
	}
	public void setSc_checkedDepartment(String sc_checkedDepartment) {
		this.sc_checkedDepartment = sc_checkedDepartment;
	}
	public String getSc_checkedJob() {
		return sc_checkedJob;
	}
	public void setSc_checkedJob(String sc_checkedJob) {
		this.sc_checkedJob = sc_checkedJob;
	}
	public String getSc_checkingDepartment() {
		return sc_checkingDepartment;
	}
	public void setSc_checkingDepartment(String sc_checkingDepartment) {
		this.sc_checkingDepartment = sc_checkingDepartment;
	}
	public String getSc_inspector() {
		return sc_inspector;
	}
	public void setSc_inspector(String sc_inspector) {
		this.sc_inspector = sc_inspector;
	}
	public String getSc_hiddenCondition() {
		return sc_hiddenCondition;
	}
	public void setSc_hiddenCondition(String sc_hiddenCondition) {
		this.sc_hiddenCondition = sc_hiddenCondition;
	}
	public String getSc_isHave() {
		return sc_isHave;
	}
	public void setSc_isHave(String sc_isHave) {
		this.sc_isHave = sc_isHave;
	}
	public String getSc_fixMeasure() {
		return sc_fixMeasure;
	}
	public void setSc_fixMeasure(String sc_fixMeasure) {
		this.sc_fixMeasure = sc_fixMeasure;
	}
	public String getSc_startTime() {
		return sc_startTime;
	}
	public void setSc_startTime(String sc_startTime) {
		this.sc_startTime = sc_startTime;
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
	public String getSc_companyName() {
		return sc_companyName;
	}
	public void setSc_companyName(String sc_companyName) {
		this.sc_companyName = sc_companyName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSc_checkedTimes() {
		return sc_checkedTimes;
	}
	public void setSc_checkedTimes(int sc_checkedTimes) {
		this.sc_checkedTimes = sc_checkedTimes;
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
	public String getSco_fixresult() {
		return sco_fixresult;
	}
	public void setSco_fixresult(String sco_fixresult) {
		this.sco_fixresult = sco_fixresult;
	}
	public String getSco_acceptopinion() {
		return sco_acceptopinion;
	}
	public void setSco_acceptopinion(String sco_acceptopinion) {
		this.sco_acceptopinion = sco_acceptopinion;
	}
	public String getSco_acceptanceDepartment() {
		return sco_acceptanceDepartment;
	}
	public void setSco_acceptanceDepartment(String sco_acceptanceDepartment) {
		this.sco_acceptanceDepartment = sco_acceptanceDepartment;
	}
	public String getSco_acceptanceTime() {
		return sco_acceptanceTime;
	}
	public void setSco_acceptanceTime(String sco_acceptanceTime) {
		this.sco_acceptanceTime = sco_acceptanceTime;
	}
	public String getSco_accepters() {
		return sco_accepters;
	}
	public void setSco_accepters(String sco_accepters) {
		this.sco_accepters = sco_accepters;
	}
	public String getSco_completeTablePeople() {
		return sco_completeTablePeople;
	}
	public void setSco_completeTablePeople(String sco_completeTablePeople) {
		this.sco_completeTablePeople = sco_completeTablePeople;
	}
	public String getSco_completeTableTime() {
		return sco_completeTableTime;
	}
	public void setSco_completeTableTime(String sco_completeTableTime) {
		this.sco_completeTableTime = sco_completeTableTime;
	}
	public String getSco_isAccept() {
		return sco_isAccept;
	}
	public void setSco_isAccept(String sco_isAccept) {
		this.sco_isAccept = sco_isAccept;
	}
}


