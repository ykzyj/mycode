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
* @date 2015��8��21�� ����4:10:03 
* @version V1.0   
*/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TablePrimaryKey {
	public enum AutoIncrement{YES,NO};
	
	/**
     * ��ɫ����
     * @return
     */
	AutoIncrement PrimaryKeyType();
}


