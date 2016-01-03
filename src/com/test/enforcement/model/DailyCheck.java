package com.sunnyit.enforcement.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: DailyCheck.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:20:23 
* @version V1.0   
*/
public class DailyCheck implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String c_id;      						//主键                  
	  private String ck_id;            					//检查编号        
	  private String ck_checkingdepartment;        		//检查部门
	  private String ck_time;                      		//检查时间
	  private String ck_checkeddepartment;         		//被检查企业
	  private String ck_checkPlace;
	  private String ck_sceneresponsible;          		//企业现场负责人
	  private String ck_position;                  		//职务
	  private String ck_telephone;                 		//联系电话
	  private String ck_scenecondition;            		//现场检查情况
	  private String haveopinion;                  		//是否有意见
	  private String ck_fixnowing;                 		//立即整改
	  private String ck_fixdeadline;               		//限期整改
	  private String ck_fixstart_time;             		//整改开始日期
	  private String ck_fixend_time;               		//整改结束日期
	  private String ck_checkpeople;               		//检查人员
	  private String ck_completechecktablepeople;  		//填表人
	  private String ck_completechecktabletime;    		//填表时间
	  private String ck_state;                     		//检查状态
	  private String ck_fixMeasure;
	  private String ck_fixCondition;
	  private String ck_fixMeasurePerson;
	  private String ck_fixMeasureTime;					
	  private String ck_fixConditionPerson;
	  private String ck_fixConditionTime;
	  private String ck_acceptopinion;					//验收意见
	  private String ck_acceptanceDept;					//验收部门
	  private String ck_acceptanceTime;					//验收时间
	  private String ck_superviseOpinion;
	  private String ck_supervisePerson;
	  private String ck_superviseTime;
	  private String isSupervise;
	  private String ck_reviewcondition;           		//现场复查情况
	  private String ck_reviewopinion;             		//复查意见
	  private String ck_reviewdepartment;          		//复查部门
	  private String ck_reviewtime;                		//复查时间
	  private String ck_reviewprople;              		//复查人员
	  private String ck_completereviewtablepeople; 		//填表人
	  private String ck_completereviewtabletime;   		//填表时间
	  private String remark;            				//备注
	  private String ck_userId;                        //填写检查表用户ID
	  private String ck_startime;      					//检查开始时间（时分秒）
      private String ck_endtime;      					//检查结束时间（时分秒）
      
      
	  
	  
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
	public String getCk_supervisePerson() {
		return ck_supervisePerson;
	}
	public void setCk_supervisePerson(String ck_supervisePerson) {
		this.ck_supervisePerson = ck_supervisePerson;
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
	public String getCk_checkingdepartment() {
		return ck_checkingdepartment;
	}
	public void setCk_checkingdepartment(String ck_checkingdepartment) {
		this.ck_checkingdepartment = ck_checkingdepartment;
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
	public String getCk_checkPlace() {
		return ck_checkPlace;
	}
	public void setCk_checkPlace(String ck_checkPlace) {
		this.ck_checkPlace = ck_checkPlace;
	}
	public String getCk_sceneresponsible() {
		return ck_sceneresponsible;
	}
	public void setCk_sceneresponsible(String ck_sceneresponsible) {
		this.ck_sceneresponsible = ck_sceneresponsible;
	}
	public String getCk_position() {
		return ck_position;
	}
	public void setCk_position(String ck_position) {
		this.ck_position = ck_position;
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
	public String getCk_checkpeople() {
		return ck_checkpeople;
	}
	public void setCk_checkpeople(String ck_checkpeople) {
		this.ck_checkpeople = ck_checkpeople;
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
	public String getCk_fixMeasure() {
		return ck_fixMeasure;
	}
	public void setCk_fixMeasure(String ck_fixMeasure) {
		this.ck_fixMeasure = ck_fixMeasure;
	}
	public String getCk_fixCondition() {
		return ck_fixCondition;
	}
	public void setCk_fixCondition(String ck_fixCondition) {
		this.ck_fixCondition = ck_fixCondition;
	}
	public String getCk_fixMeasurePerson() {
		return ck_fixMeasurePerson;
	}
	public void setCk_fixMeasurePerson(String ck_fixMeasurePerson) {
		this.ck_fixMeasurePerson = ck_fixMeasurePerson;
	}
	public String getCk_fixMeasureTime() {
		return ck_fixMeasureTime;
	}
	public void setCk_fixMeasureTime(String ck_fixMeasureTime) {
		this.ck_fixMeasureTime = ck_fixMeasureTime;
	}
	public String getCk_fixConditionPerson() {
		return ck_fixConditionPerson;
	}
	public void setCk_fixConditionPerson(String ck_fixConditionPerson) {
		this.ck_fixConditionPerson = ck_fixConditionPerson;
	}
	public String getCk_fixConditionTime() {
		return ck_fixConditionTime;
	}
	public void setCk_fixConditionTime(String ck_fixConditionTime) {
		this.ck_fixConditionTime = ck_fixConditionTime;
	}
	public String getCk_acceptopinion() {
		return ck_acceptopinion;
	}
	public void setCk_acceptopinion(String ck_acceptopinion) {
		this.ck_acceptopinion = ck_acceptopinion;
	}
	public String getCk_acceptanceDept() {
		return ck_acceptanceDept;
	}
	public void setCk_acceptanceDept(String ck_acceptanceDept) {
		this.ck_acceptanceDept = ck_acceptanceDept;
	}
	public String getCk_acceptanceTime() {
		return ck_acceptanceTime;
	}
	public void setCk_acceptanceTime(String ck_acceptanceTime) {
		this.ck_acceptanceTime = ck_acceptanceTime;
	}
	public String getCk_superviseOpinion() {
		return ck_superviseOpinion;
	}
	public void setCk_superviseOpinion(String ck_superviseOpinion) {
		this.ck_superviseOpinion = ck_superviseOpinion;
	}
	public String getCk_superviseTime() {
		return ck_superviseTime;
	}
	public void setCk_superviseTime(String ck_superviseTime) {
		this.ck_superviseTime = ck_superviseTime;
	}
	public String getIsSupervise() {
		return isSupervise;
	}
	public void setIsSupervise(String isSupervise) {
		this.isSupervise = isSupervise;
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
}


