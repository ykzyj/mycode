package com.sunnyit.law.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: PublishInfo.java 
* @Package com.sunnyit.law.model 
* @Description: TODO
* @author yk
* @date 2015年9月11日 上午11:50:52 
* @version V1.0   
*/
public class PublishInfo implements Serializable {
	@TablePrimaryKey(PrimaryKeyType = AutoIncrement.NO)
	private String id;
	private String title;
	private String content;
	private String publishTime;
	private String publisher;
	private String publishDeptId;
	private String publishDeptName;
	private String infoIndex;
	private String keyword;
	private String infoType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublishDeptId() {
		return publishDeptId;
	}
	public void setPublishDeptId(String publishDeptId) {
		this.publishDeptId = publishDeptId;
	}
	public String getPublishDeptName() {
		return publishDeptName;
	}
	public void setPublishDeptName(String publishDeptName) {
		this.publishDeptName = publishDeptName;
	}
	public String getInfoIndex() {
		return infoIndex;
	}
	public void setInfoIndex(String infoIndex) {
		this.infoIndex = infoIndex;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
}


