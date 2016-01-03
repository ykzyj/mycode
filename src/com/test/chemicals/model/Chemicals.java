package com.sunnyit.chemicals.model;

import java.io.Serializable;

/**   
* @Title: Chemicals.java 
* @Package com.sunnyit.chemicals.model 
* @Description: TODO
* @author yk
* @date 2015��11��10�� ����2:39:42 
* @version V1.0   
*/
public class Chemicals implements Serializable {
	private String c_no;					//���
	private String c_name;				//����
	private String c_aliasname;			//����
	private String c_showname;			//��ʾ����
	private String c_cas;				//cas��
	private String c_isvirulen;			//�Ƿ�綾
	
	private String c_physical;			//������
	private String c_danger;			//Σ��������
	private String c_aidmeasures;		//���ȴ�ʩ
	private String c_firecontrol;		//������ʩ
	private String c_leak;				//й¶Ӧ������
	private String c_operation;			//��������
	private String c_contact;			//�Ӵ�����
	private String c_stabreact;			//�ȶ��Ժͷ�Ӧ����
	private String c_toxicological;		//����ѧ����
	private String c_transport;			//������Ϣ
	private String c_treatment;			//������
	
	public String getC_no() {
		return c_no;
	}
	public void setC_no(String c_no) {
		this.c_no = c_no;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_aliasname() {
		return c_aliasname;
	}
	public void setC_aliasname(String c_aliasname) {
		this.c_aliasname = c_aliasname;
	}
	public String getC_showname() {
		return c_showname;
	}
	public void setC_showname(String c_showname) {
		this.c_showname = c_showname;
	}
	public String getC_cas() {
		return c_cas;
	}
	public void setC_cas(String c_cas) {
		this.c_cas = c_cas;
	}
	public String getC_isvirulen() {
		return c_isvirulen;
	}
	public void setC_isvirulen(String c_isvirulen) {
		this.c_isvirulen = c_isvirulen;
	}
	public String getC_physical() {
		return c_physical;
	}
	public void setC_physical(String c_physical) {
		this.c_physical = c_physical;
	}
	public String getC_danger() {
		return c_danger;
	}
	public void setC_danger(String c_danger) {
		this.c_danger = c_danger;
	}
	public String getC_aidmeasures() {
		return c_aidmeasures;
	}
	public void setC_aidmeasures(String c_aidmeasures) {
		this.c_aidmeasures = c_aidmeasures;
	}
	public String getC_firecontrol() {
		return c_firecontrol;
	}
	public void setC_firecontrol(String c_firecontrol) {
		this.c_firecontrol = c_firecontrol;
	}
	public String getC_leak() {
		return c_leak;
	}
	public void setC_leak(String c_leak) {
		this.c_leak = c_leak;
	}
	public String getC_operation() {
		return c_operation;
	}
	public void setC_operation(String c_operation) {
		this.c_operation = c_operation;
	}
	public String getC_contact() {
		return c_contact;
	}
	public void setC_contact(String c_contact) {
		this.c_contact = c_contact;
	}
	public String getC_stabreact() {
		return c_stabreact;
	}
	public void setC_stabreact(String c_stabreact) {
		this.c_stabreact = c_stabreact;
	}
	public String getC_toxicological() {
		return c_toxicological;
	}
	public void setC_toxicological(String c_toxicological) {
		this.c_toxicological = c_toxicological;
	}
	public String getC_transport() {
		return c_transport;
	}
	public void setC_transport(String c_transport) {
		this.c_transport = c_transport;
	}
	public String getC_treatment() {
		return c_treatment;
	}
	public void setC_treatment(String c_treatment) {
		this.c_treatment = c_treatment;
	}
}


