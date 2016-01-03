package com.sunnyit.enforcement.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: SpecialCheck.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015年9月26日 上午9:58:32 
* @version V1.0   
*/
public class SpecialCheck implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String c_id; // 主键
	private String ck_id; // 专项检查编号
	private String ck_specialcheckname; // 专项检查名称
	private String ck_checkingdepartment; // 检查组织单位名称
	private String ck_checkgroupid; // 检查组编号
	private String ck_leader; // 带队领导
	private String ck_position; // 职务
	private String ck_time; // 检查时间
	private String ck_checkeddepartment; // 被检查企业
	private String ck_sceneresponsible; // 企业现场负责人
	private String ck_duty; // 职务
	private String ck_telephone; // 联系电话
	private String ck_scenecondition; // 现场检查情况
	private String haveopinion; // 有无处理意见
	private String ck_fixnowing; // 立即整改
	private String ck_fixdeadline; // 限期整改
	private String ck_fixstart_time; // 整改开始日期
	private String ck_fixend_time; // 整改结束日期
	private String ck_completechecktablepeople; // 检查表填表人
	private String ck_completechecktabletime; // 检查表填表时间
	private String ck_state; // 检查状态：无需整改，未整改，整改中，已整改，已复查，已立案，已销号

	private String ck_reviewcondition; // 现场复查情况
	private String ck_reviewopinion; // 复查意见
	private String ck_reviewdepartment; // 复查部门
	private String ck_reviewtime; // 复查时间
	private String ck_reviewprople; // 复查人员
	private String ck_completereviewtablepeople; // 复查表填表人
	private String ck_completereviewtabletime; // 复查表填表时间
	private String remark; // 备注

	private String ck_repairmethod; // 整改措施
	private String ck_repaircondition; // 整改情况
	private String ck_repairconditionPerson; // 整改情况填写人
	private String ck_repairconditionTime; // 整改情况填写时间
	private String ck_repairconditionDept; // 整改情况验收部门
	private String ck_repairconditionDeptTime; // 整改情况 验收时间
	private String ck_repairPerson; // 整改措施填写人
	private String ck_repairTime; // 整改措施填写时间
	private String ck_duinfo; // 督促信息

	// 以下为新加的
	private String ck_site; // 检查场所
	private String ck_showReviewBook; // 复查文书是否可见,SHOW/HIDE

	private String ck_userId; // 填写检查表用户ID
	private String ck_startime;//检查时间（时分秒）
	private String ck_endtime;//检查时间（时分秒）
	
	public String getCk_startime() {
		return ck_startime;
	}
	public void setCk_startime(String ck_startime) {
		this.ck_startime = ck_startime;
	}
	public String getCk_endtime() {
		return ck_endtime;
	}
	public void setCk_endtime(String ck_endtime) {
		this.ck_endtime = ck_endtime;
	}
	public String getCk_repairconditionPerson() {
		return ck_repairconditionPerson;
	}
	public void setCk_repairconditionPerson(String ck_repairconditionPerson) {
		this.ck_repairconditionPerson = ck_repairconditionPerson;
	}
	public String getCk_repairconditionTime() {
		return ck_repairconditionTime;
	}
	public void setCk_repairconditionTime(String ck_repairconditionTime) {
		this.ck_repairconditionTime = ck_repairconditionTime;
	}
	public String getCk_repairconditionDept() {
		return ck_repairconditionDept;
	}
	public void setCk_repairconditionDept(String ck_repairconditionDept) {
		this.ck_repairconditionDept = ck_repairconditionDept;
	}
	public String getCk_repairconditionDeptTime() {
		return ck_repairconditionDeptTime;
	}
	public void setCk_repairconditionDeptTime(String ck_repairconditionDeptTime) {
		this.ck_repairconditionDeptTime = ck_repairconditionDeptTime;
	}
	public String getCk_repairPerson() {
		return ck_repairPerson;
	}
	public void setCk_repairPerson(String ck_repairPerson) {
		this.ck_repairPerson = ck_repairPerson;
	}
	public String getCk_repairTime() {
		return ck_repairTime;
	}
	public void setCk_repairTime(String ck_repairTime) {
		this.ck_repairTime = ck_repairTime;
	}
	public String getCk_site() {
		return ck_site;
	}
	public void setCk_site(String ck_site) {
		this.ck_site = ck_site;
	}
	public String getCk_showReviewBook() {
		return ck_showReviewBook;
	}
	public void setCk_showReviewBook(String ck_showReviewBook) {
		this.ck_showReviewBook = ck_showReviewBook;
	}
	public String getCk_userId() {
		return ck_userId;
	}
	public void setCk_userId(String ck_userId) {
		this.ck_userId = ck_userId;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public String getCk_specialcheckname() {
		return ck_specialcheckname;
	}
	public void setCk_specialcheckname(String ck_specialcheckname) {
		this.ck_specialcheckname = ck_specialcheckname;
	}
	public String getCk_checkingdepartment() {
		return ck_checkingdepartment;
	}
	public void setCk_checkingdepartment(String ck_checkingdepartment) {
		this.ck_checkingdepartment = ck_checkingdepartment;
	}
	public String getCk_checkgroupid() {
		return ck_checkgroupid;
	}
	public void setCk_checkgroupid(String ck_checkgroupid) {
		this.ck_checkgroupid = ck_checkgroupid;
	}
	public String getCk_leader() {
		return ck_leader;
	}
	public void setCk_leader(String ck_leader) {
		this.ck_leader = ck_leader;
	}
	public String getCk_position() {
		return ck_position;
	}
	public void setCk_position(String ck_position) {
		this.ck_position = ck_position;
	}
	public String getCk_time() {
		return ck_time;
	}
	public void setCk_time(String ck_time) {
		this.ck_time = ck_time;
	}
	public String getCk_checkeddepartment() {
		return ck_checkeddepartment;
	}
	public void setCk_checkeddepartment(String ck_checkeddepartment) {
		this.ck_checkeddepartment = ck_checkeddepartment;
	}
	public String getCk_sceneresponsible() {
		return ck_sceneresponsible;
	}
	public void setCk_sceneresponsible(String ck_sceneresponsible) {
		this.ck_sceneresponsible = ck_sceneresponsible;
	}
	public String getCk_duty() {
		return ck_duty;
	}
	public void setCk_duty(String ck_duty) {
		this.ck_duty = ck_duty;
	}
	public String getCk_telephone() {
		return ck_telephone;
	}
	public void setCk_telephone(String ck_telephone) {
		this.ck_telephone = ck_telephone;
	}
	public String getCk_scenecondition() {
		return ck_scenecondition;
	}
	public void setCk_scenecondition(String ck_scenecondition) {
		this.ck_scenecondition = ck_scenecondition;
	}
	public String getHaveopinion() {
		return haveopinion;
	}
	public void setHaveopinion(String haveopinion) {
		this.haveopinion = haveopinion;
	}
	public String getCk_fixnowing() {
		return ck_fixnowing;
	}
	public void setCk_fixnowing(String ck_fixnowing) {
		this.ck_fixnowing = ck_fixnowing;
	}
	public String getCk_fixdeadline() {
		return ck_fixdeadline;
	}
	public void setCk_fixdeadline(String ck_fixdeadline) {
		this.ck_fixdeadline = ck_fixdeadline;
	}
	public String getCk_fixstart_time() {
		return ck_fixstart_time;
	}
	public void setCk_fixstart_time(String ck_fixstart_time) {
		this.ck_fixstart_time = ck_fixstart_time;
	}
	public String getCk_fixend_time() {
		return ck_fixend_time;
	}
	public void setCk_fixend_time(String ck_fixend_time) {
		this.ck_fixend_time = ck_fixend_time;
	}
	public String getCk_completechecktablepeople() {
		return ck_completechecktablepeople;
	}
	public void setCk_completechecktablepeople(String ck_completechecktablepeople) {
		this.ck_completechecktablepeople = ck_completechecktablepeople;
	}
	public String getCk_completechecktabletime() {
		return ck_completechecktabletime;
	}
	public void setCk_completechecktabletime(String ck_completechecktabletime) {
		this.ck_completechecktabletime = ck_completechecktabletime;
	}
	public String getCk_state() {
		return ck_state;
	}
	public void setCk_state(String ck_state) {
		this.ck_state = ck_state;
	}
	public String getCk_reviewcondition() {
		return ck_reviewcondition;
	}
	public void setCk_reviewcondition(String ck_reviewcondition) {
		this.ck_reviewcondition = ck_reviewcondition;
	}
	public String getCk_reviewopinion() {
		return ck_reviewopinion;
	}
	public void setCk_reviewopinion(String ck_reviewopinion) {
		this.ck_reviewopinion = ck_reviewopinion;
	}
	public String getCk_reviewdepartment() {
		return ck_reviewdepartment;
	}
	public void setCk_reviewdepartment(String ck_reviewdepartment) {
		this.ck_reviewdepartment = ck_reviewdepartment;
	}
	public String getCk_reviewtime() {
		return ck_reviewtime;
	}
	public void setCk_reviewtime(String ck_reviewtime) {
		this.ck_reviewtime = ck_reviewtime;
	}
	public String getCk_reviewprople() {
		return ck_reviewprople;
	}
	public void setCk_reviewprople(String ck_reviewprople) {
		this.ck_reviewprople = ck_reviewprople;
	}
	public String getCk_completereviewtablepeople() {
		return ck_completereviewtablepeople;
	}
	public void setCk_completereviewtablepeople(String ck_completereviewtablepeople) {
		this.ck_completereviewtablepeople = ck_completereviewtablepeople;
	}
	public String getCk_completereviewtabletime() {
		return ck_completereviewtabletime;
	}
	public void setCk_completereviewtabletime(String ck_completereviewtabletime) {
		this.ck_completereviewtabletime = ck_completereviewtabletime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCk_repairmethod() {
		return ck_repairmethod;
	}
	public void setCk_repairmethod(String ck_repairmethod) {
		this.ck_repairmethod = ck_repairmethod;
	}
	public String getCk_repaircondition() {
		return ck_repaircondition;
	}
	public void setCk_repaircondition(String ck_repaircondition) {
		this.ck_repaircondition = ck_repaircondition;
	}
	public String getCk_duinfo() {
		return ck_duinfo;
	}
	public void setCk_duinfo(String ck_duinfo) {
		this.ck_duinfo = ck_duinfo;
	}
}


