package com.sunnyit.system.model;

import com.sunnyit.common.adapter.AdapterUtil;

/**   
* @Title: menu_item.java 
* @Package com.sunnyit.system.model 
* @Description: TODO
* @author yk
* @date 2015��8��7�� ����5:37:16 
* @version V1.0   
*/
public class menu_item{
	public menu_item(int menuImg, String menuText) {
		this.menuImg = menuImg;
		this.menuText = menuText;
	}
	private int menuImg;		//itemͼƬ
	private String menuText;	//item�ı�
	public int getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(int menuImg) {
		this.menuImg = menuImg;
	}
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
}


