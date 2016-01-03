package com.sunnyit.enforcement.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: CheckTables.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015��10��8�� ����2:10:09 
* @version V1.0   
*/
public class CheckTables {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String sc_uuId;				//����
	private String h_id;				
	private String hf_id;				//������׼�ļ�id
	private String h_content_one;		//�����Բ��Ҫ��
	private String h_content_two;		//�����Բ��Ҫ��
	private String h_content_three;		//�����Բ��Ҫ��
	private String h_content_four;		//�����Բ����Ҫ��
	private String h_content_five;		//�����Բ�5��Ҫ��
	private String h_description;		//�Բ��׼���������
	private String h_basis;				//�ο�����
	private String h_dangerLevel;		//����Σ�յȼ�
	private String h_checkCircle;		//�������
	private String h_reportLevel;		//�ϱ�����
	private String sc_tableId;
	private String sc_tableName;
	private String sc_userId;           //������û�ID
	private String sc_createTime;       //�����ʱ��
	private String h_seq;				//˳��
	
	private String h_checkResult;		//�����
	private String h_punishment;		//����
	
	
	public String getH_checkResult() {
		return h_checkResult;
	}
	public void setH_checkResult(String h_checkResult) {
		this.h_checkResult = h_checkResult;
	}
	public String getH_punishment() {
		return h_punishment;
	}
	public void setH_punishment(String h_punishment) {
		this.h_punishment = h_punishment;
	}
	public String getH_seq() {
		return h_seq;
	}
	public void setH_seq(String h_seq) {
		this.h_seq = h_seq;
	}
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
	public String getSc_tableId() {
		return sc_tableId;
	}
	public void setSc_tableId(String sc_tableId) {
		this.sc_tableId = sc_tableId;
	}
	public String getSc_tableName() {
		return sc_tableName;
	}
	public void setSc_tableName(String sc_tableName) {
		this.sc_tableName = sc_tableName;
	}
	public String getSc_userId() {
		return sc_userId;
	}
	public void setSc_userId(String sc_userId) {
		this.sc_userId = sc_userId;
	}
	public String getSc_createTime() {
		return sc_createTime;
	}
	public void setSc_createTime(String sc_createTime) {
		this.sc_createTime = sc_createTime;
	}
}


