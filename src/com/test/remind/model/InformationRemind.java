package com.sunnyit.remind.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: InformationRemind.java 
* @Package com.sunnyit.remind.model 
* @Description: TODO
* @author yk
* @date 2015��12��11�� ����10:50:17 
* @version V1.0   
*/
public class InformationRemind implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String I_ID;
	private String I_Title;
	private String I_Content;
	private String I_Time;
	private String I_HaveLook;//0δ�鿴��1�Ѳ鿴
	private String remark;
	
	public String getI_HaveLook() {
		return I_HaveLook;
	}
	public void setI_HaveLook(String i_HaveLook) {
		I_HaveLook = i_HaveLook;
	}
	public String getI_ID() {
		return I_ID;
	}
	public void setI_ID(String i_ID) {
		I_ID = i_ID;
	}
	public String getI_Title() {
		return I_Title;
	}
	public void setI_Title(String i_Title) {
		I_Title = i_Title;
	}
	public String getI_Content() {
		return I_Content;
	}
	public void setI_Content(String i_Content) {
		I_Content = i_Content;
	}
	public String getI_Time() {
		return I_Time;
	}
	public void setI_Time(String i_Time) {
		I_Time = i_Time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}


