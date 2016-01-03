package com.sunnyit.system.model;

import com.sunnyit.common.adapter.AdapterUtil;

/**   
* @Title: menu_item.java 
* @Package com.sunnyit.system.model 
* @Description: TODO
* @author yk
* @date 2015年8月7日 下午5:37:16 
* @version V1.0   
*/
public class menu_item{
	public menu_item(int menuImg, String menuText) {
		this.menuImg = menuImg;
		this.menuText = menuText;
	}
	private int menuImg;		//item图片
	private String menuText;	//item文本
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


