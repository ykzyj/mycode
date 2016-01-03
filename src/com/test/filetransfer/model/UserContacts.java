package com.sunnyit.filetransfer.model;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: UserContacts.java 
* @Package com.sunnyit.filetransfer.model 
* @Description: TODO
* @author yk
* @date 2015年12月3日 下午5:31:21 
* @version V1.0   
*/
public class UserContacts {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;			//主键
	private String userId;		//用户
	private String contactId;	//用户联系人
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


