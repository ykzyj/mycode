package com.sunnyit.hiddencheck.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: StandcheckDetail.java 
* @Package com.sunnyit.hiddencheck.model 
* @Description: TODO
* @author yk
* @date 2015年9月8日 下午2:11:52 
* @version V1.0   
*/
public class StandcheckDetail {
	private String sc_uuId;    
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String h_id;
	private String hf_id;
	private String h_content_one;
	private String h_content_two;
	private String h_content_three; 
	private String h_content_four;	
	private String h_content_five;	
	private String h_description;
	private String h_basis;	
	private String h_dangerLevel;
	private String h_checkCircle;
	private String h_reportLevel;
	private String sc_isPass;
	private String sc_state;	
	private String sc_type;
	private String sc_deadline;
	private String sc_image;	
	private String sc_fixMeasure;
	private String sc_fixcondition;
	public String getSc_uuId() {
		return sc_uuId;
	}
	public void setSc_uuId(String sc_uuId) {
		this.sc_uuId = sc_uuId;
	}
	public String getH_id() {
		return h_id;
	}
	public void setH_id(String h_id) {
		this.h_id = h_id;
	}
	public String getHf_id() {
		return hf_id;
	}
	public void setHf_id(String hf_id) {
		this.hf_id = hf_id;
	}
	public String getH_content_one() {
		return h_content_one;
	}
	public void setH_content_one(String h_content_one) {
		this.h_content_one = h_content_one;
	}
	public String getH_content_two() {
		return h_content_two;
	}
	public void setH_content_two(String h_content_two) {
		this.h_content_two = h_content_two;
	}
	public String getH_content_three() {
		return h_content_three;
	}
	public void setH_content_three(String h_content_three) {
		this.h_content_three = h_content_three;
	}
	public String getH_content_four() {
		return h_content_four;
	}
	public void setH_content_four(String h_content_four) {
		this.h_content_four = h_content_four;
	}
	public String getH_content_five() {
		return h_content_five;
	}
	public void setH_content_five(String h_content_five) {
		this.h_content_five = h_content_five;
	}
	public String getH_description() {
		return h_description;
	}
	public void setH_description(String h_description) {
		this.h_description = h_description;
	}
	public String getH_basis() {
		return h_basis;
	}
	public void setH_basis(String h_basis) {
		this.h_basis = h_basis;
	}
	public String getH_dangerLevel() {
		return h_dangerLevel;
	}
	public void setH_dangerLevel(String h_dangerLevel) {
		this.h_dangerLevel = h_dangerLevel;
	}
	public String getH_checkCircle() {
		return h_checkCircle;
	}
	public void setH_checkCircle(String h_checkCircle) {
		this.h_checkCircle = h_checkCircle;
	}
	public String getH_reportLevel() {
		return h_reportLevel;
	}
	public void setH_reportLevel(String h_reportLevel) {
		this.h_reportLevel = h_reportLevel;
	}
	public String getSc_isPass() {
		return sc_isPass;
	}
	public void setSc_isPass(String sc_isPass) {
		this.sc_isPass = sc_isPass;
	}
	public String getSc_state() {
		return sc_state;
	}
	public void setSc_state(String sc_state) {
		this.sc_state = sc_state;
	}
	public String getSc_type() {
		return sc_type;
	}
	public void setSc_type(String sc_type) {
		this.sc_type = sc_type;
	}
	public String getSc_deadline() {
		return sc_deadline;
	}
	public void setSc_deadline(String sc_deadline) {
		this.sc_deadline = sc_deadline;
	}
	public String getSc_image() {
		return sc_image;
	}
	public void setSc_image(String sc_image) {
		this.sc_image = sc_image;
	}
	public String getSc_fixMeasure() {
		return sc_fixMeasure;
	}
	public void setSc_fixMeasure(String sc_fixMeasure) {
		this.sc_fixMeasure = sc_fixMeasure;
	}
	public String getSc_fixcondition() {
		return sc_fixcondition;
	}
	public void setSc_fixcondition(String sc_fixcondition) {
		this.sc_fixcondition = sc_fixcondition;
	}
}


