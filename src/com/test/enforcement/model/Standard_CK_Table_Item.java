package com.sunnyit.enforcement.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Standard_CK_Table_Item.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015��10��10�� ����8:59:18 
* @version V1.0   
*/
public class Standard_CK_Table_Item {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;					//ÿһ����id
	private String ck_id;				//�����id
	
	private String itemId;				//���ü�����ÿһ����¼��id
	private String item_content_one;		//�����Բ��Ҫ��
	private String item_content_two;		//�����Բ��Ҫ��
	private String item_content_three;		//�����Բ��Ҫ��
	private String item_content_four;		//�����Բ����Ҫ��
	private String item_content_five;		//�����Բ�5��Ҫ��
	private String item_description;		//�Բ��׼���������
	private String item_basis;				//�ο�����
	private String item_dangerLevel;		//����Σ�յȼ�
	private String item_checkCircle;		//�������
	private String item_reportLevel;		//�ϱ�����
	
	private String item_image;			//ÿһ����¼��ͼƬ
	private String item_isQualified;	//ÿһ���Ƿ�ϸ�		YES/NO
	private String item_repairType;		//�������ͣ��������Ļ�����������	L/N
	private String item_repairLimit;	//�������ĵĽ�ֹʱ��
	private String item_repairState;	//����״̬	��������/δ����/������/������
	
	
	private String item_repairMethod;   			//���Ĵ�ʩ
	private String item_method_people;				//���Ĵ�ʩ�����
	private String item_method_time;				//���Ĵ�ʩ���ʱ��
	
	private String item_repairCondition;     		//�������
	private String item_apccept_opinion;			//�������	YES/NO
	private String item_apccept_dept;				//���ղ���
	private String item_apccept_time;				//����ʱ��
	private String item_condition_people;			//�����
	private String item_condition_time;				//���ʱ��
	
	private String item_reviewResult;		//������	YES/NO
	private String item_reviewImage;		//������Ƭ
	
	private String item_checkResult;		//�����(********)
	private String item_punishment;			//����(********)
	
	
	public String getItem_checkResult() {
		return item_checkResult;
	}
	public void setItem_checkResult(String item_checkResult) {
		this.item_checkResult = item_checkResult;
	}
	public String getItem_punishment() {
		return item_punishment;
	}
	public void setItem_punishment(String item_punishment) {
		this.item_punishment = item_punishment;
	}
	private String h_seq;
	
	public String getH_seq() {
		return h_seq;
	}
	public void setH_seq(String h_seq) {
		this.h_seq = h_seq;
	}
	public String getItem_reviewResult() {
		return item_reviewResult;
	}
	public void setItem_reviewResult(String item_reviewResult) {
		this.item_reviewResult = item_reviewResult;
	}
	public String getItem_reviewImage() {
		return item_reviewImage;
	}
	public void setItem_reviewImage(String item_reviewImage) {
		this.item_reviewImage = item_reviewImage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItem_content_one() {
		return item_content_one;
	}
	public void setItem_content_one(String item_content_one) {
		this.item_content_one = item_content_one;
	}
	public String getItem_content_two() {
		return item_content_two;
	}
	public void setItem_content_two(String item_content_two) {
		this.item_content_two = item_content_two;
	}
	public String getItem_content_three() {
		return item_content_three;
	}
	public void setItem_content_three(String item_content_three) {
		this.item_content_three = item_content_three;
	}
	public String getItem_content_four() {
		return item_content_four;
	}
	public void setItem_content_four(String item_content_four) {
		this.item_content_four = item_content_four;
	}
	public String getItem_content_five() {
		return item_content_five;
	}
	public void setItem_content_five(String item_content_five) {
		this.item_content_five = item_content_five;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public String getItem_basis() {
		return item_basis;
	}
	public void setItem_basis(String item_basis) {
		this.item_basis = item_basis;
	}
	public String getItem_dangerLevel() {
		return item_dangerLevel;
	}
	public void setItem_dangerLevel(String item_dangerLevel) {
		this.item_dangerLevel = item_dangerLevel;
	}
	public String getItem_checkCircle() {
		return item_checkCircle;
	}
	public void setItem_checkCircle(String item_checkCircle) {
		this.item_checkCircle = item_checkCircle;
	}
	public String getItem_reportLevel() {
		return item_reportLevel;
	}
	public void setItem_reportLevel(String item_reportLevel) {
		this.item_reportLevel = item_reportLevel;
	}
	public String getItem_image() {
		return item_image;
	}
	public void setItem_image(String item_image) {
		this.item_image = item_image;
	}
	public String getItem_isQualified() {
		return item_isQualified;
	}
	public void setItem_isQualified(String item_isQualified) {
		this.item_isQualified = item_isQualified;
	}
	public String getItem_repairType() {
		return item_repairType;
	}
	public void setItem_repairType(String item_repairType) {
		this.item_repairType = item_repairType;
	}
	public String getItem_repairLimit() {
		return item_repairLimit;
	}
	public void setItem_repairLimit(String item_repairLimit) {
		this.item_repairLimit = item_repairLimit;
	}
	public String getItem_repairState() {
		return item_repairState;
	}
	public void setItem_repairState(String item_repairState) {
		this.item_repairState = item_repairState;
	}
	public String getItem_repairMethod() {
		return item_repairMethod;
	}
	public void setItem_repairMethod(String item_repairMethod) {
		this.item_repairMethod = item_repairMethod;
	}
	public String getItem_method_people() {
		return item_method_people;
	}
	public void setItem_method_people(String item_method_people) {
		this.item_method_people = item_method_people;
	}
	public String getItem_method_time() {
		return item_method_time;
	}
	public void setItem_method_time(String item_method_time) {
		this.item_method_time = item_method_time;
	}
	public String getItem_repairCondition() {
		return item_repairCondition;
	}
	public void setItem_repairCondition(String item_repairCondition) {
		this.item_repairCondition = item_repairCondition;
	}
	public String getItem_apccept_opinion() {
		return item_apccept_opinion;
	}
	public void setItem_apccept_opinion(String item_apccept_opinion) {
		this.item_apccept_opinion = item_apccept_opinion;
	}
	public String getItem_apccept_dept() {
		return item_apccept_dept;
	}
	public void setItem_apccept_dept(String item_apccept_dept) {
		this.item_apccept_dept = item_apccept_dept;
	}
	public String getItem_apccept_time() {
		return item_apccept_time;
	}
	public void setItem_apccept_time(String item_apccept_time) {
		this.item_apccept_time = item_apccept_time;
	}
	public String getItem_condition_people() {
		return item_condition_people;
	}
	public void setItem_condition_people(String item_condition_people) {
		this.item_condition_people = item_condition_people;
	}
	public String getItem_condition_time() {
		return item_condition_time;
	}
	public void setItem_condition_time(String item_condition_time) {
		this.item_condition_time = item_condition_time;
	}
}


