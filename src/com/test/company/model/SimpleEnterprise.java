package com.sunnyit.company.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: Enterprise.java 
* @Package com.sunnyit.company.model 
* @Description: TODO
* @author yk
* @date 2015��9��6�� ����12:52:38 
* @version V1.0   
*/
public class SimpleEnterprise implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String e_Id;						//ID
	private String e_companyName; 				//��ҵ����
	private String e_companyAddress;			//��ҵ��ַ
	private String e_managerlayer;				//��ܲ㼶
	private String e_departmentId;				//��ҵ���ܲ���id
	private String e_departmentName;			//��ҵ���ܲ�������
	private String e_localId;					//��������Id
	private String e_localName;					//������������
	private String e_businessCode; 				//����Ӫҵִ��֤��
	private String e_legal_representative;		//����������
	private String e_contact_phone;				//��ϵ�绰
	private String e_safe_person;				//��ȫ������
	private String e_safe_person_phone;			//��ȫ�������ֻ�����
	private String e_belongIndustry;			//������ҵ 
	private String e_mangementMethod;			//������Ӫ��ʽ
	private String e_companyProperty;			//��ҵ����
	private String remark;						//��ע
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


