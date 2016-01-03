package com.sunnyit.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
* @Title: TablePrimaryKey.java 
* @Package com.sunnyit.common.annotation 
* @Description: TODO
* @author yk
* @date 2015年8月21日 下午4:10:03 
* @version V1.0   
*/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TablePrimaryKey {
	public enum AutoIncrement{YES,NO};
	
	/**
     * 颜色属性
     * @return
     */
	AutoIncrement PrimaryKeyType();
}


