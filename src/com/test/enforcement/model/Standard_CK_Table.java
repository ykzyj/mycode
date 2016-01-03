package com.sunnyit.enforcement.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Standard_CK_Table.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015年10月10日 上午8:58:45 
* @version V1.0   
*/
public class Standard_CK_Table implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String ck_id;			//检查表的id
	private String ck_seq;			//检查表的编号如：20150831001
	private String ck_name;			//检查表的名称
	private String companyId;		//企业id
	private String companyName;		//企业名称
	private String ck_site;			//检查场所
	private String ck_people;		//检查人员
	private String ck_time;			//检查时间
	private String ck_state;		//检查状态  检查中，无需整改，未整改，整改中，已整改，已复查，已立案，已销号
	
	private String ck_deptId;		//检查部门id
	private String ck_dept;			//检查部门
	private String isExistDanger;	//是否存在隐患
	
	private String ck_repairmethod;   	//整改措施
	private String ck_repaircondition;  //整改情况
	private String ck_duinfo;			//督促信息
	private String ck_duTime;			//督促时间
	
	private String ck_reviewDept;		//复查部门
	private String ck_reviewTime;		//复查时间
	private String ck_reviewPeople;		//复查人员
	
	private String ck_deadLine;			//整改截止时间
	private String ck_showReviewBook;	//复查文书是否可见
	private String ck_userId;           //填写检查表用户ID
	private String ck_startime;         //检查时间(时分秒)
	private String ck_endtime;          //检查时间(时分秒)
	
	private String ck_legal_representative;//单位法定代表人
	private String ck_telphone;         //联系电话
	private String ck_enterpriseaddress;//被检查单位地址
	
	public String getCk_legal_representative() {
		return ck_legal_representative;
	}
	public void setCk_legal_representative(String ck_legal_representative) {
		this.ck_legal_representative = ck_legal_representative;
	}
	public String getCk_telphone() {
		return ck_telphone;
	}
	public void setCk_telphone(String ck_telphone) {
		this.ck_telphone = ck_telphone;
	}
	public String getCk_enterpriseaddress() {
		return ck_enterpriseaddress;
	}
	public void setCk_enterpriseaddress(String ck_enterpriseaddress) {
		this.ck_enterpriseaddress = ck_enterpriseaddress;
	}
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
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public String getCk_seq() {
		return ck_seq;
	}
	public void setCk_seq(String ck_seq) {
		this.ck_seq = ck_seq;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCk_site() {
		return ck_site;
	}
	public void setCk_site(String ck_site) {
		this.ck_site = ck_site;
	}
	public String getCk_people() {
		return ck_people;
	}
	public void setCk_people(String ck_people) {
		this.ck_people = ck_people;
	}
	public String getCk_time() {
		return ck_time;
	}
	public void setCk_time(String ck_time) {
		this.ck_time = ck_time;
	}
	public String getCk_state() {
		return ck_state;
	}
	public void setCk_state(String ck_state) {
		this.ck_state = ck_state;
	}
	public String getCk_deptId() {
		return ck_deptId;
	}
	public void setCk_deptId(String ck_deptId) {
		this.ck_deptId = ck_deptId;
	}
	public String getCk_dept() {
		return ck_dept;
	}
	public void setCk_dept(String ck_dept) {
		this.ck_dept = ck_dept;
	}
	public String getIsExistDanger() {
		return isExistDanger;
	}
	public void setIsExistDanger(String isExistDanger) {
		this.isExistDanger = isExistDanger;
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
	public String getCk_duTime() {
		return ck_duTime;
	}
	public void setCk_duTime(String ck_duTime) {
		this.ck_duTime = ck_duTime;
	}
	public String getCk_reviewDept() {
		return ck_reviewDept;
	}
	public void setCk_reviewDept(String ck_reviewDept) {
		this.ck_reviewDept = ck_reviewDept;
	}
	public String getCk_reviewTime() {
		return ck_reviewTime;
	}
	public void setCk_reviewTime(String ck_reviewTime) {
		this.ck_reviewTime = ck_reviewTime;
	}
	public String getCk_reviewPeople() {
		return ck_reviewPeople;
	}
	public void setCk_reviewPeople(String ck_reviewPeople) {
		this.ck_reviewPeople = ck_reviewPeople;
	}
	public String getCk_deadLine() {
		return ck_deadLine;
	}
	public void setCk_deadLine(String ck_deadLine) {
		this.ck_deadLine = ck_deadLine;
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
}


