package com.sunnyit.enforcement.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Standard_CK_Table_Item.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015年10月10日 上午8:59:18 
* @version V1.0   
*/
public class Standard_CK_Table_Item {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;					//每一条的id
	private String ck_id;				//检查表的id
	
	private String itemId;				//引用检查表中每一条记录的id
	private String item_content_one;		//隐患自查Ⅰ级要素
	private String item_content_two;		//隐患自查Ⅱ级要素
	private String item_content_three;		//隐患自查Ⅲ级要素
	private String item_content_four;		//隐患自查Ⅳ级要素
	private String item_content_five;		//隐患自查5级要素
	private String item_description;		//自查标准项具体描述
	private String item_basis;				//参考依据
	private String item_dangerLevel;		//隐患危险等级
	private String item_checkCircle;		//检查周期
	private String item_reportLevel;		//上报级别
	
	private String item_image;			//每一条记录的图片
	private String item_isQualified;	//每一条是否合格		YES/NO
	private String item_repairType;		//整改类型，限期整改还是立即整改	L/N
	private String item_repairLimit;	//限期整改的截止时间
	private String item_repairState;	//整改状态	无需整改/未整改/已整改/整改中
	
	
	private String item_repairMethod;   			//整改措施
	private String item_method_people;				//整改措施填表人
	private String item_method_time;				//整改措施填表时间
	
	private String item_repairCondition;     		//整改情况
	private String item_apccept_opinion;			//验收意见	YES/NO
	private String item_apccept_dept;				//验收部门
	private String item_apccept_time;				//验收时间
	private String item_condition_people;			//填表人
	private String item_condition_time;				//填表时间
	
	private String item_reviewResult;		//复查结果	YES/NO
	private String item_reviewImage;		//复查照片
	
	private String item_checkResult;		//检查结果(********)
	private String item_punishment;			//罚则(********)
	
	
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


