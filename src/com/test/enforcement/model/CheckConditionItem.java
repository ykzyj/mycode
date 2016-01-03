package com.sunnyit.enforcement.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: CheckConditionItem.java 
* @Package com.sunnyit.enforcement.model 
* @Description: TODO
* @author yk
* @date 2015��11��27�� ����3:31:16 
* @version V1.0   
*/
public class CheckConditionItem implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;
	private String checkNo;
	private String checkContent;
	private String checkImg; 
	private String checkItemState;//0:�ϸ�1���������ģ�2����������
	private String checkDate;
	private String checkCkId;
	public String getCheckCkId() {
		return checkCkId;
	}
	public void setCheckCkId(String checkCkId) {
		this.checkCkId = checkCkId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	public String getCheckImg() {
		return checkImg;
	}
	public void setCheckImg(String checkImg) {
		this.checkImg = checkImg;
	}
	public String getCheckItemState() {
		return checkItemState;
	}
	public void setCheckItemState(String checkItemState) {
		this.checkItemState = checkItemState;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}


