package com.sunnyit.filetransfer.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: UserContacts.java 
* @Package com.sunnyit.filetransfer.model 
* @Description: TODO
* @author yk
* @date 2015��12��3�� ����5:31:21 
* @version V1.0   
*/
public class UserContacts {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;			//����
	private String userId;		//�û�
	private String contactId;	//�û���ϵ��
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
}


