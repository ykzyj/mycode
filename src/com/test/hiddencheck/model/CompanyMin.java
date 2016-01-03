package com.sunnyit.hiddencheck.model;

import java.io.Serializable;

import com.sunnyit.common.annotation.TablePrimaryKey;
import com.sunnyit.common.annotation.TablePrimaryKey.AutoIncrement;

/**   
* @Title: SelfCheck.java 
* @Package com.sunnyit.hiddencheck.model 
* @Description: TODO
* @author yk
* @date 2015年9月8日 下午2:08:59 
* @version V1.0   
*/
public class CompanyMin{
	private String e_Id;
	private String e_companyName;
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
}


