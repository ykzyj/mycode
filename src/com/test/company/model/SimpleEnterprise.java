package com.sunnyit.company.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Enterprise.java 
* @Package com.sunnyit.company.model 
* @Description: TODO
* @author yk
* @date 2015年9月6日 下午12:52:38 
* @version V1.0   
*/
public class SimpleEnterprise implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String e_Id;						//ID
	private String e_companyName; 				//企业名称
	private String e_companyAddress;			//企业地址
	private String e_managerlayer;				//监管层级
	private String e_departmentId;				//行业主管部门id
	private String e_departmentName;			//行业主管部门名称
	private String e_localId;					//所属区域Id
	private String e_localName;					//所属区域名称
	private String e_businessCode; 				//工商营业执照证号
	private String e_legal_representative;		//法定代表人
	private String e_contact_phone;				//联系电话
	private String e_safe_person;				//安全负责人
	private String e_safe_person_phone;			//安全负责人手机号码
	private String e_belongIndustry;			//所属行业 
	private String e_mangementMethod;			//生产经营方式
	private String e_companyProperty;			//企业性质
	private String remark;						//备注
	public String getE_Id() {
		return e_Id;
	}
	public void setE_Id(String e_Id) {
		this.e_Id = e_Id;
	}
	public String getE_companyName() {
		return e_companyName;
	}
	public void setE_companyName(String e_companyName) {
		this.e_companyName = e_companyName;
	}
	public String getE_companyAddress() {
		return e_companyAddress;
	}
	public void setE_companyAddress(String e_companyAddress) {
		this.e_companyAddress = e_companyAddress;
	}
	public String getE_managerlayer() {
		return e_managerlayer;
	}
	public void setE_managerlayer(String e_managerlayer) {
		this.e_managerlayer = e_managerlayer;
	}
	public String getE_departmentId() {
		return e_departmentId;
	}
	public void setE_departmentId(String e_departmentId) {
		this.e_departmentId = e_departmentId;
	}
	public String getE_departmentName() {
		return e_departmentName;
	}
	public void setE_departmentName(String e_departmentName) {
		this.e_departmentName = e_departmentName;
	}
	public String getE_localId() {
		return e_localId;
	}
	public void setE_localId(String e_localId) {
		this.e_localId = e_localId;
	}
	public String getE_localName() {
		return e_localName;
	}
	public void setE_localName(String e_localName) {
		this.e_localName = e_localName;
	}
	public String getE_businessCode() {
		return e_businessCode;
	}
	public void setE_businessCode(String e_businessCode) {
		this.e_businessCode = e_businessCode;
	}
	public String getE_legal_representative() {
		return e_legal_representative;
	}
	public void setE_legal_representative(String e_legal_representative) {
		this.e_legal_representative = e_legal_representative;
	}
	public String getE_contact_phone() {
		return e_contact_phone;
	}
	public void setE_contact_phone(String e_contact_phone) {
		this.e_contact_phone = e_contact_phone;
	}
	public String getE_belongIndustry() {
		return e_belongIndustry;
	}
	public void setE_belongIndustry(String e_belongIndustry) {
		this.e_belongIndustry = e_belongIndustry;
	}
	public String getE_mangementMethod() {
		return e_mangementMethod;
	}
	public void setE_mangementMethod(String e_mangementMethod) {
		this.e_mangementMethod = e_mangementMethod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getE_companyProperty() {
		return e_companyProperty;
	}
	public void setE_companyProperty(String e_companyProperty) {
		this.e_companyProperty = e_companyProperty;
	}
	public String getE_safe_person() {
		return e_safe_person;
	}
	public void setE_safe_person(String e_safe_person) {
		this.e_safe_person = e_safe_person;
	}
	public String getE_safe_person_phone() {
		return e_safe_person_phone;
	}
	public void setE_safe_person_phone(String e_safe_person_phone) {
		this.e_safe_person_phone = e_safe_person_phone;
	}
}


